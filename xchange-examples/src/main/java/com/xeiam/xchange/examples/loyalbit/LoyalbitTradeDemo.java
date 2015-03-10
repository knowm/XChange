package com.xeiam.xchange.examples.loyalbit;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class LoyalbitTradeDemo {

  public static void main(String[] args) throws IOException {
    Exchange loyalbitExchange = LoyalbitExampleUtils.createTestExchange();
    PollingTradeService tradeService = loyalbitExchange.getPollingTradeService();

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // Place a limit sell order at high price
    LimitOrder sellOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.03"), CurrencyPair.BTC_USD, null, null, new BigDecimal("2000"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(sellOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
    System.out.println("Open Orders: " + tradeService.getOpenOrders());

    // Place a limit sell order at low price
    LimitOrder lowSellOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.03"), CurrencyPair.BTC_USD, null, null, new BigDecimal("150.00"));
    String lowOrderReturnValue = tradeService.placeLimitOrder(lowSellOrder);
    System.out.println("Limit Order return value: " + lowOrderReturnValue);

    final UserTrades tradeHistory = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());

    System.out.println("Trade history: " + tradeHistory);
  }
}
