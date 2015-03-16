package com.xeiam.xchange.examples.campbx.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.campbx.CampBXExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Camp BX exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 */
public class CampBXTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange campbx = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());

    ExchangeSpecification exSpec = campbx.getExchangeSpecification();
    exSpec.setUserName("XChange");
    exSpec.setPassword("The Java API");

    PollingTradeService tradeService = campbx.getPollingTradeService();

    printOpenOrders(tradeService);

    LimitOrder lo = new LimitOrder((OrderType.ASK), new BigDecimal("0.1"), CurrencyPair.BTC_USD, "", null, new BigDecimal("28.99"));
    String rv = tradeService.placeLimitOrder(lo);
    System.out.println("Limit Order return value: " + rv);
    // place a limit sell order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.1"), CurrencyPair.BTC_USD, "", null, new BigDecimal("110"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    // boolean cancelResult = tradeService.cancelOrder("Buy-1234");
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }
}
