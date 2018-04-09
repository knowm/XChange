package org.knowm.xchange.examples.wex.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.wex.WexExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.wex.v3.dto.trade.WexCancelOrderResult;
import org.knowm.xchange.wex.v3.dto.trade.WexOrder;
import org.knowm.xchange.wex.v3.dto.trade.WexPlaceOrderResult;
import org.knowm.xchange.wex.v3.service.WexTradeServiceRaw;

/** @author Matija Mazi */
public class WexTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange btce = WexExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    TradeService tradeService = exchange.getTradeService();

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("0.1"),
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("1023.45"));

    String limitOrderReturnValue = null;
    try {
      limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Limit Order return value: " + limitOrderReturnValue);

      printOpenOrders(tradeService);

      // Cancel the added order
      boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
      System.out.println("Canceling returned " + cancelResult);
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }

    printOpenOrders(tradeService);
  }

  private static void raw(Exchange exchange) throws IOException {

    WexTradeServiceRaw tradeService = (WexTradeServiceRaw) exchange.getTradeService();

    printRawOpenOrders(tradeService);

    // place buy order
    WexOrder.Type type = WexOrder.Type.buy;
    String pair = "btc_usd";
    WexOrder wexOrder =
        new WexOrder(0, null, new BigDecimal("1"), new BigDecimal("0.1"), type, pair);

    WexPlaceOrderResult result = null;
    try {
      result = tradeService.placeBTCEOrder(wexOrder);
      System.out.println("placeBTCEOrder return value: " + result);

      printRawOpenOrders(tradeService);

      // Cancel the added order
      WexCancelOrderResult cancelResult = tradeService.cancelBTCEOrder(result.getOrderId());
      System.out.println("Canceling returned " + cancelResult);
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }

    printRawOpenOrders(tradeService);
  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void printRawOpenOrders(WexTradeServiceRaw tradeService) throws IOException {

    Map<Long, WexOrder> openOrders = tradeService.getBTCEActiveOrders(null);
    for (Map.Entry<Long, WexOrder> entry : openOrders.entrySet()) {
      System.out.println("ID: " + entry.getKey() + ", Order:" + entry.getValue());
    }
  }
}
