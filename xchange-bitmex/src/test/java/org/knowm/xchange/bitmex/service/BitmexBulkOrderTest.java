package org.knowm.xchange.bitmex.service;

import static org.knowm.xchange.bitmex.BitmexPrompt.PERPETUAL;
import static org.knowm.xchange.currency.CurrencyPair.XBT_USD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.Bitmex;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.utils.CertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Nikita Belenkiy on 18/05/2018. */
public class BitmexBulkOrderTest {
  private static final Logger logger = LoggerFactory.getLogger(BitmexBulkOrderTest.class);

  public static final String SYMBOL = XBT_USD.base.toString() + XBT_USD.counter.toString();

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

    OrderBook orderBook = marketDataService.getOrderBook(XBT_USD, PERPETUAL);
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
    //    LimitOrder limitOrder =
    //        new LimitOrder(
    //            Order.OrderType.ASK,
    //            originalOrderSize,
    //            XBT_USD,
    //            nosOrdId,
    //            new Date(),
    //            price);

    List<Bitmex.PlaceOrderCommand> commands = new ArrayList<>();
    commands.add(
        new Bitmex.PlaceOrderCommand(
            SYMBOL,
            BitmexSide.SELL.toString(),
            originalOrderSize.intValue(),
            price,
            null,
            nosOrdId,
            null,
            null));

    List<BitmexPrivateOrder> bitmexPrivateOrders = tradeService.placeLimitOrderBulk(commands);
    for (BitmexPrivateOrder bitmexPrivateOrder : bitmexPrivateOrders) {}

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
