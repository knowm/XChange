package org.knowm.xchange.bitmex.service;

import static org.knowm.xchange.bitmex.BitmexPrompt.PERPETUAL;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.CertHelper;

/** @author Nikita Belenkiy on 18/05/2018. */
public class BitmexOrderReplaceTest {

  @Test
  @Ignore
  public void testOrderReplace() throws Exception {
    CertHelper.trustAllCerts();
    BitmexExchange exchange =
        (BitmexExchange) ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class);
    ExchangeSpecification defaultExchangeSpecification = exchange.getDefaultExchangeSpecification();

    defaultExchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", true);

    defaultExchangeSpecification.setApiKey("QW8Ao_gx38e-8KFvDkFn-Ym4");
    defaultExchangeSpecification.setSecretKey("tn7rpzvOXSKThZD0f-xXehtydt4OTHZVf42gCCyxPixiiVOb");

    defaultExchangeSpecification.setShouldLoadRemoteMetaData(true);
    defaultExchangeSpecification.setProxyHost("localhost");
    defaultExchangeSpecification.setProxyPort(9999);
    exchange.applySpecification(defaultExchangeSpecification);

    BitmexMarketDataService marketDataService =
        (BitmexMarketDataService) exchange.getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.XBT_USD, PERPETUAL);
    //    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currency.ADA,
    // Currency.BTC), BitmexPrompt.QUARTERLY);
    //    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currency.BTC,
    // Currency.USD), BitmexPrompt.BIQUARTERLY);

    System.out.println("orderBook = " + orderBook);

    BitmexTradeService tradeService = (BitmexTradeService) exchange.getTradeService();
    String nosOrdId = System.currentTimeMillis() + "";
    BigDecimal originalOrderSize = new BigDecimal("30");
    //    BigDecimal price = new BigDecimal("10000");
    BigDecimal price = orderBook.getBids().get(0).getLimitPrice().add(new BigDecimal("0.5"));
    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.ASK,
            originalOrderSize,
            CurrencyPair.XBT_USD,
            nosOrdId,
            new Date(),
            price);
    String orderId = tradeService.placeLimitOrder(limitOrder);

    Thread.sleep(5000);

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
    //        System.out.println("cancelled = " + bitmexPrivateOrders.get(0));
    //            service.getTicker(Currency.XBT.getSymbol());
  }
}
