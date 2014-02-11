package com.xeiam.xchange.examples.cexio.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.cexio.CexIODemoUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CexIODemoUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.BID, BigDecimal.ONE, "GHS", "BTC", "", null, MoneyUtils.parse("BTC 0.00015600"));
    System.out.println("Trying to place: " + limitOrder);
    String orderId = "0";
    try {
      orderId = tradeService.placeLimitOrder(limitOrder);
      System.out.println("New Limit Order ID: " + orderId);
    } catch (ExchangeException e) {
      System.out.println(e);
    }
    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(orderId);
    System.out.println("Canceling order id=" + orderId + " returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
  }

}
