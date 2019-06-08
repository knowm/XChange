package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.core.StreamingExchange;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.bitmex.service.BitmexMarketDataService;
import org.knowm.xchange.bitmex.service.BitmexTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.utils.CertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.knowm.xchange.bitmex.BitmexPrompt.PERPETUAL;

/**
 * @author Nikita Belenkiy on 18/05/2018.
 */
public class BitmexOrderReplaceTest {
    private static final Logger logger = LoggerFactory.getLogger(BitmexOrderReplaceTest.class);

    @Test
    @Ignore
    public void testOrderReplace() throws Exception {
        CertHelper.trustAllCerts();
        BitmexStreamingExchange exchange =
                (BitmexStreamingExchange) ExchangeFactory.INSTANCE.createExchange(BitmexStreamingExchange.class);
        ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();

        defaultExchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", true);

        defaultExchangeSpecification.setApiKey("QW8Ao_gx38e-8KFvDkFn-Ym4");
        defaultExchangeSpecification.setSecretKey("tn7rpzvOXSKThZD0f-xXehtydt4OTHZVf42gCCyxPixiiVOb");

        defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);
        defaultExchangeSpecification.setProxyHost("localhost");
        defaultExchangeSpecification.setProxyPort(9999);

        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_HOST, "localhost");
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.SOCKS_PROXY_PORT, 8889);

        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ACCEPT_ALL_CERITICATES, true);
//        defaultExchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.ENABLE_LOGGING_HANDLER, true);

        exchange.applySpecification(defaultExchangeSpecification);
        exchange.connect().blockingAwait();
        BitmexMarketDataService marketDataService =
                (BitmexMarketDataService) exchange.getMarketDataService();

        BitmexTradeService tradeService = (BitmexTradeService)exchange.getTradeService();

        final BitmexStreamingMarketDataService streamingMarketDataService = (BitmexStreamingMarketDataService) exchange.getStreamingMarketDataService();
//        streamingMarketDataService.authenticate();
        CurrencyPair xbtUsd = CurrencyPair.XBT_USD;

        streamingMarketDataService.getExecutions("XBTUSD").subscribe(bitmexExecution -> {
            logger.info("!!!!EXECUTION!!!! = {}", bitmexExecution);
        });
        OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.XBT_USD, PERPETUAL);
        //    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currency.ADA,
        // Currency.BTC), BitmexPrompt.QUARTERLY);
        //    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currency.BTC,
        // Currency.USD), BitmexPrompt.BIQUARTERLY);

        System.out.println("orderBook = " + orderBook);

        String nosOrdId = System.currentTimeMillis() + "";
        BigDecimal originalOrderSize = new BigDecimal("300");
        //    BigDecimal price = new BigDecimal("10000");
        BigDecimal price = orderBook.getBids().get(0).getLimitPrice().add(new BigDecimal("100"));
        BitmexPrivateOrder xbtusd = tradeService.placeLimitOrder("XBTUSD", originalOrderSize, price, BitmexSide.SELL, nosOrdId, null);
        logger.info("!!!!!PRIVATE_ORDER!!!! {}",xbtusd);
        Thread.sleep(5000);
        System.out.println();
        System.out.println();
        System.out.println();


        logger.info("Replacing");
        String replacedOrderId = nosOrdId + "replace";
        BitmexPrivateOrder replaceBPO = tradeService.replaceLimitOrder("XBTUSD", originalOrderSize.divide(BigDecimal.valueOf(2)), price, null, replacedOrderId, nosOrdId);
        logger.info("!!!!!PRIVATE_ORDER_REPLACE!!!! {}",xbtusd);
        Thread.sleep(10000);
        System.out.println();
        System.out.println();
        System.out.println();
        List<BitmexPrivateOrder> bitmexPrivateOrders = tradeService.cancelBitmexOrder(null, replacedOrderId);
        for (BitmexPrivateOrder bitmexPrivateOrder : bitmexPrivateOrders) {
            logger.info("!!!!!PRIVATE_ORDER_CANCEL!!!! {}",bitmexPrivateOrder);

        }
        Thread.sleep(10000);

        //    BitmexPrivateOrder bitmexPrivateOrder =
        //        tradeService.replaceLimitOrder(
        //            "XBTUSD",
        //            originalOrderSize.divide(new BigDecimal("2")),
        //            null,
        //            orderId,
        //            //            null, null,
        //            nosOrdId + "replace",
        //            nosOrdId);
        //    System.out.println("bitmexPrivateOrder = " + bitmexPrivateOrder);
        tradeService.cancelAllOrders();
        exchange.disconnect();
    }
}
