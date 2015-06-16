package com.xeiam.xchange.examples.taurus.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.taurus.dto.trade.TaurusOrder;
import com.xeiam.xchange.taurus.service.polling.TaurusTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.taurus.TaurusDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

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
    PollingTradeService tradeService = taurus.getPollingTradeService();

    generic(tradeService);
    raw((TaurusTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

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

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

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
