package com.xeiam.xchange.examples.bitso.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.dto.trade.BitsoOrder;
import com.xeiam.xchange.bitso.service.polling.BitsoTradeServiceRaw;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.bitso.BitsoDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Bitso exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 *
 * @author Piotr Ładyżyński
 */
public class BitsoTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitso = BitsoDemoUtils.createExchange();
    PollingTradeService tradeService = bitso.getPollingTradeService();

    generic(tradeService);
    raw((BitsoTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal("0.01"), new CurrencyPair(Currency.BTC, Currency.MXN), "", null,
        new BigDecimal("5000.00"));
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

  private static void raw(BitsoTradeServiceRaw tradeService) throws IOException {

    printRawOpenOrders(tradeService);

    // place a limit buy order
    BitsoOrder order = tradeService.sellBitsoOrder(new BigDecimal("0.01"), new BigDecimal("5000.00"));
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
