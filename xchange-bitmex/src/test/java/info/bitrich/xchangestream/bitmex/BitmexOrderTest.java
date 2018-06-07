package info.bitrich.xchangestream.bitmex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.bitmex.service.BitmexTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Nikita Belenkiy on 18/05/2018.
 */
public class BitmexOrderTest {
    private static final CurrencyPair xbtUsd = CurrencyPair.XBT_USD;
    private static final Logger LOG = LoggerFactory.getLogger(BitmexTest.class);

    private String apiKey;
    private String secretKey;

    private BitmexTradeService tradeService;

    @Before
    public void setup() throws IOException {
        loadKeys();
        BitmexExchange exchange = ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class);
        ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();

        defaultExchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", true);
        defaultExchangeSpecification.setApiKey(apiKey);
        defaultExchangeSpecification.setSecretKey(secretKey);
        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);

        exchange.applySpecification(defaultExchangeSpecification);
        tradeService = (BitmexTradeService) exchange.getTradeService();
    }

    private void loadKeys() throws IOException {
        try {
            Properties properties = new Properties();
            FileInputStream input = new FileInputStream("secret.keys");
            properties.load(input);
            apiKey = properties.getProperty("api.key");
            secretKey = properties.getProperty("secret.key");
            input.close();
        } catch (FileNotFoundException e) {
            LOG.error("Please create secret.keys file from secret.keys.origin");
            throw e;
        }
    }

    private String generateOrderId() {
        return System.currentTimeMillis() + "";
    }

    private String placeLimitOrder(String nosOrdId, String price, String size, Order.OrderType type) throws Exception {
        LimitOrder limitOrder =
                new LimitOrder(
                        type,
                        new BigDecimal(size),
                        xbtUsd,
                        nosOrdId,
                        new Date(),
                        new BigDecimal(price));
        String orderId = tradeService.placeLimitOrder(limitOrder);
        LOG.info("Order was placed with id = {}", orderId);
        return orderId;
    }

    private BitmexPrivateOrder cancelLimitOrder(String nosOrdId) {
        List<BitmexPrivateOrder> bitmexPrivateOrders =
                tradeService.cancelBitmexOrder(null, nosOrdId);
        Assert.assertEquals(1, bitmexPrivateOrders.size());
        BitmexPrivateOrder order = bitmexPrivateOrders.get(0);
        LOG.info("Order was cancelled = {}", order);
        return order;
    }

    private void checkPrivateOrder(String orderId, String price, String size, BitmexSide side,
                                   BitmexPrivateOrder bitmexPrivateOrder) {
        Assert.assertEquals(orderId, bitmexPrivateOrder.getId());
        Assert.assertEquals(price, bitmexPrivateOrder.getPrice().toString());
        Assert.assertEquals(size, bitmexPrivateOrder.getVolume().toString());
        Assert.assertEquals(side, bitmexPrivateOrder.getSide());
    }

    @Test
    public void shouldPlaceLimitOrder() throws Exception {
        final String nosOrdId = generateOrderId();// todo : price based on a current order book
        String orderId = placeLimitOrder(nosOrdId, "10000", "10", Order.OrderType.ASK);
        Assert.assertNotNull(orderId);
        cancelLimitOrder(nosOrdId);
    }

    @Test
    public void shouldCancelOrder() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId, "10000", "10", Order.OrderType.ASK);
        BitmexPrivateOrder bitmexPrivateOrder = cancelLimitOrder(nosOrdId);

        checkPrivateOrder(orderId, "10000", "10", BitmexSide.SELL, bitmexPrivateOrder);
    }

    @Test
    public void shouldReplaceOrder() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId, "10000", "10", Order.OrderType.ASK);

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

        checkPrivateOrder(orderId, "10000", "5", BitmexSide.SELL, bitmexPrivateOrder);
        cancelLimitOrder(replaceId);
    }

    @Test
    public void shouldCancelAllOrders() throws Exception {
        final String nosOrdId = generateOrderId();
        String orderId = placeLimitOrder(nosOrdId, "10000", "10", Order.OrderType.ASK);
        final String nosOrdId2 = generateOrderId();
        String orderId2 = placeLimitOrder(nosOrdId2, "5000", "5", Order.OrderType.BID);

        List<BitmexPrivateOrder> bitmexPrivateOrders = tradeService.cancelAllOrders();
        Assert.assertEquals(2, bitmexPrivateOrders.size());

        checkPrivateOrder(orderId, "10000", "10", BitmexSide.SELL, bitmexPrivateOrders.get(0));
        checkPrivateOrder(orderId2, "5000", "5", BitmexSide.BUY, bitmexPrivateOrders.get(1));
    }
}