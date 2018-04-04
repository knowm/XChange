package org.knowm.xchange.examples.bitso.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.dto.trade.BitsoOrder;
import org.knowm.xchange.bitso.service.BitsoTradeServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.bitso.BitsoDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Bitso exchange with authentication
 *   <li>Enter, review and cancel limit orders
 * </ul>
 *
 * @author Piotr Ładyżyński
 */
public class BitsoTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitso = BitsoDemoUtils.createExchange();
    TradeService tradeService = bitso.getTradeService();

    generic(tradeService);
    raw((BitsoTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal("0.01"),
            new CurrencyPair(Currency.BTC, Currency.MXN),
            "",
            null,
            new BigDecimal("5000.00"));
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

  private static void raw(BitsoTradeServiceRaw tradeService) throws IOException {

    printRawOpenOrders(tradeService);

    // place a limit buy order
    BitsoOrder order =
        tradeService.sellBitsoOrder(new BigDecimal("0.01"), new BigDecimal("5000.00"));
    System.out.println("BitsoOrder return value: " + order);

    printRawOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelBitsoOrder(order.getId());
    System.out.println("Canceling returned " + cancelResult);

    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(BitsoTradeServiceRaw tradeService) throws IOException {

    BitsoOrder[] openOrders = tradeService.getBitsoOpenOrders();
    System.out.println("Open Orders: " + openOrders.length);
    for (BitsoOrder order : openOrders) {
      System.out.println(order.toString());
    }
  }
}
