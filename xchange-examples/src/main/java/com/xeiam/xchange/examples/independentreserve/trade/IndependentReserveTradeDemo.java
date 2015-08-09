package com.xeiam.xchange.examples.independentreserve.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.examples.independentreserve.IndependentReserveDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Author: Kamil Zbikowski Date: 4/14/15
 */
public class IndependentReserveTradeDemo {
  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange independentReserve = IndependentReserveDemoUtils.createExchange();
    PollingTradeService tradeService = independentReserve.getPollingTradeService();

    generic(tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException, InterruptedException {
    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((Order.OrderType.ASK), new BigDecimal(".01"), CurrencyPair.BTC_USD, "", null, new BigDecimal("1000.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);

    UserTrades tradeHistory = tradeService.getTradeHistory();
    System.out.println("Trade history: " + tradeHistory.toString());
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

}
