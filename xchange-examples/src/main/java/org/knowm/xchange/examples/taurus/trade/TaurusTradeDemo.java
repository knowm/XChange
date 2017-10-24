package org.knowm.xchange.examples.taurus.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.taurus.TaurusDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.taurus.dto.trade.TaurusOrder;
import org.knowm.xchange.taurus.service.TaurusTradeServiceRaw;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Taurus exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 */
public class TaurusTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange taurus = TaurusDemoUtils.createExchange();
    TradeService tradeService = taurus.getTradeService();

    generic(tradeService);
    raw((TaurusTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    printOpenOrders(tradeService);

    // place a limit sell order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal(".01"), CurrencyPair.BTC_CAD, null, null, new BigDecimal("400.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void raw(TaurusTradeServiceRaw tradeService) throws IOException {

    printRawOpenOrders(tradeService);

    // place a limit sell order
    TaurusOrder order = tradeService.sellTaurusOrder(new BigDecimal("0.01"), new BigDecimal("400.00"));
    System.out.println("TaurusOrder return value: " + order);

    printRawOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelTaurusOrder(order.getId());
    System.out.println("Canceling returned " + cancelResult);

    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(TaurusTradeServiceRaw tradeService) throws IOException {

    TaurusOrder[] openOrders = tradeService.getTaurusOpenOrders();
    System.out.println("Open Orders: " + openOrders.length);
    for (TaurusOrder order : openOrders) {
      System.out.println(order.toString());
    }
  }
}
