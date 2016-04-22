package org.knowm.xchange.examples.bitstamp.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.service.polling.BitstampTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.bitstamp.BitstampDemoUtils;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitstamp exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 */
public class BitstampTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitstamp = BitstampDemoUtils.createExchange();
    PollingTradeService tradeService = bitstamp.getPollingTradeService();

    generic(tradeService);
    raw((BitstampTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal(".001"), CurrencyPair.BTC_USD, "", null, new BigDecimal("1000.00"));
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

  private static void raw(BitstampTradeServiceRaw tradeService) throws IOException {

    printRawOpenOrders(tradeService);

    // place a limit buy order
    BitstampOrder order = tradeService.sellBitstampOrder(new BigDecimal(".001"), new BigDecimal("1000.00"));
    System.out.println("BitstampOrder return value: " + order);

    printRawOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelBitstampOrder(order.getId());
    System.out.println("Canceling returned " + cancelResult);

    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(BitstampTradeServiceRaw tradeService) throws IOException {

    BitstampOrder[] openOrders = tradeService.getBitstampOpenOrders();
    System.out.println("Open Orders: " + openOrders.length);
    for (BitstampOrder order : openOrders) {
      System.out.println(order.toString());
    }
  }
}
