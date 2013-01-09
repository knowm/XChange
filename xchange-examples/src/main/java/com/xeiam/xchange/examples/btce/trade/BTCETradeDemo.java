package com.xeiam.xchange.examples.btce.trade;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.btce.BTCEDemoUtils;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * @author Matija Mazi <br/>
 */
public class BTCETradeDemo {

  public static void main(String[] args) {

    Exchange btce = BTCEDemoUtils.createExchange();
    PollingTradeService tradeService = btce.getPollingTradeService();

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.1"), "BTC", "USD", MoneyUtils.parseFiat("USD 99.025"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(PollingTradeService tradeService) {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }
}
