package com.xeiam.xchange.examples.clevercoin.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinCancelOrder;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinOpenOrder;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinOrder;
import com.xeiam.xchange.clevercoin.service.polling.CleverCoinTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.clevercoin.CleverCoinDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to CleverCoin exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 */
public class CleverCoinTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange clevercoin = CleverCoinDemoUtils.createExchange();
    PollingTradeService tradeService = clevercoin.getPollingTradeService();

    generic(tradeService);
    raw((CleverCoinTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal(".01"), CurrencyPair.BTC_EUR, "", null, new BigDecimal("9999.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    if (true)
      return;

    printOpenOrders(tradeService);

  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void raw(CleverCoinTradeServiceRaw tradeService) throws IOException {

    printRawOpenOrders(tradeService);
    // place a limit buy order
    CleverCoinOpenOrder order = tradeService.createCleverCoinOrder("ask", new BigDecimal(".01"), new BigDecimal("10000.00"));
    System.out.println("Clevercoin return value: " + order);

    printRawOpenOrders(tradeService);

    // Cancel the added order
    System.out.println(order.getOrderId());

    CleverCoinCancelOrder cancelResult = tradeService.cancelCleverCoinOrder(Integer.valueOf(order.getOrderId()));
    System.out.println("Canceling returned: " + cancelResult.getResult());
    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(CleverCoinTradeServiceRaw tradeService) throws IOException {

    CleverCoinOrder[] openOrders = tradeService.getCleverCoinOpenOrders();
    System.out.println("Open Orders: " + openOrders.length);
    for (CleverCoinOrder order : openOrders) {
      System.out.println(order.toString());
    }
  }
}
