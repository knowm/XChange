package org.knowm.xchange.examples.kraken.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.dto.trade.results.KrakenCancelOrderResult.KrakenCancelOrderResponse;
import org.knowm.xchange.kraken.service.KrakenTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class KrakenCancelOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    TradeService tradeService = krakenExchange.getTradeService();

    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal(".01"),
            CurrencyPair.BTC_LTC,
            "",
            null,
            new BigDecimal("51.25"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    System.out.println("Open Orders: " + tradeService.getOpenOrders().toString());
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getTradeService();

    System.out.println("Open Orders: " + tradeService.getKrakenOpenOrders());

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal(".01"),
            CurrencyPair.BTC_LTC,
            "",
            null,
            new BigDecimal("51.25"));
    KrakenOrderResponse limitOrderReturnValue = tradeService.placeKrakenLimitOrder(limitOrder);

    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    Map<String, KrakenOrder> openOrders = tradeService.getKrakenOpenOrders();
    System.out.println("Open Orders: " + openOrders);

    // Cancel the added order
    List<String> transactionIds = limitOrderReturnValue.getTransactionIds();
    if (transactionIds != null && !transactionIds.isEmpty()) {
      KrakenCancelOrderResponse cancelResult =
          tradeService.cancelKrakenOrder(transactionIds.get(0));
      System.out.println("Canceling returned " + cancelResult);
      System.out.println("Open Orders: " + tradeService.getKrakenOpenOrders());
    }
  }
}
