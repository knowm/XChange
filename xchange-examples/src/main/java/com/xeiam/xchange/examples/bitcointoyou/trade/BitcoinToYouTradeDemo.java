package com.xeiam.xchange.examples.bitcointoyou.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;
import com.xeiam.xchange.bitcointoyou.service.polling.BitcoinToYouTradeServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * IMPORTANT NOTE: the sleep of 5 sec between each call is necessary due to a API limitation.
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to BitcoinToYou exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class BitcoinToYouTradeDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange bitcoinToYou = com.xeiam.xchange.examples.bitcointoyou.InteractiveAuthenticatedExchange.createInstanceFromDefaultInput();
    PollingTradeService tradeService = bitcoinToYou.getPollingTradeService();

    generic(tradeService);
    raw((BitcoinToYouTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException, InterruptedException {

    printOpenOrders(tradeService);

    Thread.sleep(5000);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((Order.OrderType.ASK), new BigDecimal("0.01"), CurrencyPair.BTC_BRL, "", null, new BigDecimal("4999.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    Thread.sleep(5000);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException, InterruptedException {

    Thread.sleep(5000);
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void raw(BitcoinToYouTradeServiceRaw tradeService) throws IOException, InterruptedException {

    printRawOpenOrders(tradeService);

    Thread.sleep(1000);

    LimitOrder limitOrder = new LimitOrder((Order.OrderType.BID), new BigDecimal("0.01"), new CurrencyPair(Currencies.LTC, Currencies.BRL), "", null,
        new BigDecimal("1"));

    // place a limit buy order
    BitcoinToYouOrder[] order = tradeService.placeBitcoinToYouLimitOrder(limitOrder);
    System.out.println("BitcoinToYouOrder return value: " + order[0]);

    printRawOpenOrders(tradeService);

    Thread.sleep(5000);

    // Cancel the added order
    BitcoinToYouOrder cancelResult = tradeService.cancelBitcoinToYouLimitOrder(String.valueOf(order[0].getId()));
    System.out.println("Canceling returned " + cancelResult);

    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(BitcoinToYouTradeServiceRaw tradeService) throws IOException, InterruptedException {

    Thread.sleep(5000);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> openOrdersResult = tradeService.getBitcoinToYouUserOrders("OPEN");

    BitcoinToYouOrder[] openOrders = openOrdersResult.getTheReturn();

    System.out.println("Open Orders: " + openOrders.length);
    for (BitcoinToYouOrder order : openOrders) {
      System.out.println(order.toString());
    }
  }
}
