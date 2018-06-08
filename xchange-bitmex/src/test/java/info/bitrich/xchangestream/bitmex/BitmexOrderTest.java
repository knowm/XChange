package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.util.LocalExchangeConfig;
import info.bitrich.xchangestream.util.PropsLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.bitmex.service.BitmexMarketDataService;
import org.knowm.xchange.bitmex.service.BitmexTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.knowm.xchange.bitmex.BitmexPrompt.PERPETUAL;

/**
 * @author Nikita Belenkiy on 18/05/2018.
 */
public class BitmexOrderTest {
    private static final CurrencyPair xbtUsd = CurrencyPair.XBT_USD;
    private static final Logger LOG = LoggerFactory.getLogger(BitmexTest.class);

    private static final BigDecimal priceShift = new BigDecimal("1000");

    private BigDecimal testAskPrice;
    private BigDecimal testBidPrice;

    private BitmexTradeService tradeService;

    @Before
    public void setup() throws IOException {
        LocalExchangeConfig localConfig = PropsLoader.loadKeys("secret.keys", "secret.keys.origin");
        BitmexExchange exchange = ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class);
        ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();

        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ACCEPT_ALL_CERITICATES, true);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ENABLE_LOGGING_HANDLER, true);

        defaultExchangeSpecification.setApiKey(localConfig.getApiKey());
        defaultExchangeSpecification.setSecretKey(localConfig.getSecretKey());
        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);

        exchange.applySpecification(defaultExchangeSpecification);

        BitmexMarketDataService marketDataService =
                (BitmexMarketDataService) exchange.getMarketDataService();

        OrderBook orderBook = marketDataService.getOrderBook(xbtUsd, PERPETUAL);
        List<LimitOrder> asks = orderBook.getAsks();
        // todo : for the streaming service best ask is at 0 pos
        BigDecimal topPriceAsk = getPrice(asks, asks.size() - 1);
        BigDecimal topPriceBid = getPrice(orderBook.getBids(), 0);

        LOG.info("Got best ask = {}, best bid = {}", topPriceAsk, topPriceBid);
        Assert.assertTrue("Got empty order book", topPriceAsk != null || topPriceBid != null);

        if (topPriceAsk != null) {
            testAskPrice = topPriceAsk.add(priceShift);
            testBidPrice = topPriceAsk.subtract(priceShift);
        } else {
            testAskPrice = topPriceBid.add(priceShift);
            testBidPrice = topPriceBid.subtract(priceShift);
        }

        tradeService = (BitmexTradeService) exchange.getTradeService();
    }

    private BigDecimal getPrice(List<LimitOrder> side, int pos) {
        if (!side.isEmpty()) {
            return side.get(pos).getLimitPrice();
        }
        return null;
    }


    private String generateOrderId() {
        return System.currentTimeMillis() + "";
    }

    private String placeLimitOrder(String nosOrdId, BigDecimal price, String size, Order.OrderType type) throws Exception {
        LimitOrder limitOrder =
                new LimitOrder(
                        type,
                        new BigDecimal(size),
                        xbtUsd,
                        nosOrdId,
                        new Date(),
                        price);
        String orderId = tradeService.placeLimitOrder(limitOrder);
        LOG.info("Order was placed with id = {}", orderId);
        return orderId;
    }

    private BitmexPrivateOrder cancelLimitOrder(String nosOrdId) {
        List<BitmexPrivateOrder> bitmexPrivateOrders =
                tradeService.cancelBitmexOrder(null, nosOrdId);
        Assert.assertEquals(1, bitmexPrivateOrders.size());
        BitmexPrivateOrder order = bitmexPrivateOrders.get(0);
        Assert.assertEquals(BitmexPrivateOrder.OrderStatus.Canceled, order.getOrderStatus());
        LOG.info("Order was cancelled = {}", order);
        return order;
    }

    private void checkPrivateOrder(String orderId, BigDecimal price, String size, BitmexSide side,
                                   BitmexPrivateOrder bitmexPrivateOrder) {
        Assert.assertEquals(orderId, bitmexPrivateOrder.getId());
        Assert.assertEquals(price, bitmexPrivateOrder.getPrice());
        Assert.assertEquals(size, bitmexPrivateOrder.getVolume().toString());
        Assert.assertEquals(side, bitmexPrivateOrder.getSide());
    }

    @Test
    public void shouldPlaceLimitOrder() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId, testAskPrice, "10", Order.OrderType.ASK);
        Assert.assertNotNull(orderId);
        cancelLimitOrder(nosOrdId);
    }

    @Test
    public void shouldCancelOrder() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId, testAskPrice, "10", Order.OrderType.ASK);
        BitmexPrivateOrder bitmexPrivateOrder = cancelLimitOrder(nosOrdId);

        checkPrivateOrder(orderId, testAskPrice, "10", BitmexSide.SELL, bitmexPrivateOrder);
    }

    @Test
    public void shouldReplaceOrder() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId, testAskPrice, "10", Order.OrderType.ASK);

        final String replaceId = nosOrdId + "replace";
        BitmexPrivateOrder bitmexPrivateOrder =
                tradeService.replaceLimitOrder(
                        "XBTUSD",
                        new BigDecimal("5"),
                        null,
                        orderId,
                        replaceId,
                        nosOrdId);
        LOG.info("Order was replaced = {}", bitmexPrivateOrder);

        checkPrivateOrder(orderId, testAskPrice, "5", BitmexSide.SELL, bitmexPrivateOrder);
        cancelLimitOrder(replaceId);
    }

    @Test
    public void shouldCancelAllOrders() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId, testAskPrice, "10", Order.OrderType.ASK);
        final String nosOrdId2 = generateOrderId();
        String orderId2 = placeLimitOrder(nosOrdId2, testBidPrice, "5", Order.OrderType.BID);

        List<BitmexPrivateOrder> bitmexPrivateOrders = tradeService.cancelAllOrders();
        Assert.assertEquals(2, bitmexPrivateOrders.size());

        checkPrivateOrder(orderId, testAskPrice, "10", BitmexSide.SELL, bitmexPrivateOrders.get(0));
        checkPrivateOrder(orderId2, testBidPrice, "5", BitmexSide.BUY, bitmexPrivateOrders.get(1));
    }

    @Test
    public void shouldFillPlacedOrder() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId,
                testBidPrice.add(priceShift.multiply(new BigDecimal("2"))),
                "10", Order.OrderType.BID);
        Assert.assertNotNull(orderId);

        List<BitmexPrivateOrder> bitmexPrivateOrders =
                tradeService.cancelBitmexOrder(null, nosOrdId);
        Assert.assertEquals(1, bitmexPrivateOrders.size());

        BitmexPrivateOrder order = bitmexPrivateOrders.get(0);
        Assert.assertEquals(BitmexPrivateOrder.OrderStatus.Filled, order.getOrderStatus());
    }
}