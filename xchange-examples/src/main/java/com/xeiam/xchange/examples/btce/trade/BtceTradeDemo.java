package com.xeiam.xchange.examples.btce.trade;

import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.btce.BtceDemoUtils;
import com.xeiam.xchange.service.trade.polling.PollingTradeService;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * @author Matija Mazi <br/>
 */
public class BtceTradeDemo {

  public static void main(String[] args) {

    Exchange btce = BtceDemoUtils.createExchange();
    PollingTradeService tradeService = btce.getPollingTradeService();

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((Order.OrderType.BID), BigDecimal.ONE, "BTC", "USD", MoneyUtils.parseFiat("USD 1.25"));
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
