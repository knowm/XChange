package org.knowm.xchange.examples.loyalbit;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;

public class LoyalbitTradeDemo {

  public static void main(String[] args) throws IOException {
    Exchange loyalbitExchange = LoyalbitExampleUtils.createTestExchange();
    TradeService tradeService = loyalbitExchange.getTradeService();

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
