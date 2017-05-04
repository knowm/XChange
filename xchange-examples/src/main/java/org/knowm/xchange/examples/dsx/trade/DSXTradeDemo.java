package org.knowm.xchange.examples.dsx.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.dsx.DSXExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;

/**
 * @author Mikhail Wall
 */
public class DSXTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange dsx = DSXExamplesUtils.createExchange();
    generic(dsx);

  }

  private static void generic(Exchange exchange) throws IOException {

    TradeService tradeService = exchange.getTradeService();

    printOpenOrders(tradeService);

    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.1"), CurrencyPair.BTC_USD, "", null, new BigDecimal
        ("1600.64"));

    String limitOrderReturnValue = null;
    try {
      limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Limit Order return value: " + limitOrderReturnValue);

      printOpenOrders(tradeService);

      boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
      System.out.println("Canceling returned " + cancelResult);
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }

    printOpenOrders(tradeService);

  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());


  }
}
