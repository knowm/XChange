package com.xeiam.xchange.examples.loyalbit;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.loyalbit.LoyalbitExampleUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class LoyalbitTradeDemo {

  public static void main(String[] args) throws IOException {
    Exchange loyalbitExchange = LoyalbitExampleUtils.createTestExchange();
    generic(loyalbitExchange);
  }

  private static void generic(Exchange loyalbitExchange) throws IOException {
    PollingTradeService tradeService = loyalbitExchange.getPollingTradeService();

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // place a limit sell order
    LimitOrder sellOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.05"), CurrencyPair.BTC_USD, null, null, new BigDecimal("2000"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(sellOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    System.out.println("Open Orders: " + tradeService.getOpenOrders());


  }
}
