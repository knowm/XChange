package org.knowm.xchange.examples.campbx.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.campbx.CampBXExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Camp BX exchange with authentication
 *   <li>Enter, review and cancel limit orders
 * </ul>
 */
public class CampBXTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange campbx = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());

    ExchangeSpecification exSpec = campbx.getExchangeSpecification();
    exSpec.setUserName("XChange");
    exSpec.setPassword("The Java API");

    TradeService tradeService = campbx.getTradeService();

    printOpenOrders(tradeService);

    LimitOrder lo =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal("0.1"),
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("28.99"));
    String rv = tradeService.placeLimitOrder(lo);
    System.out.println("Limit Order return value: " + rv);
    // place a limit sell order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal("0.1"),
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("110"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    // boolean cancelResult = tradeService.cancelOrder("Buy-1234");
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }
}
