package org.knowm.xchange.examples.bitso.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
//import org.knowm.xchange.bitso.dto.trade.BitsoAllOrders;
import org.knowm.xchange.bitso.dto.trade.BitsoOrder;
//import org.knowm.xchange.bitso.dto.trade.Payload;
import org.knowm.xchange.bitso.service.BitsoTradeServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.bitso.BitsoDemoUtils;
import org.knowm.xchange.service.account.AccountService;
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
//    AccountService accountService=bitso.getAccountService();
//    AccountInfo accountInfo=accountService.getAccountInfo();
//    System.out.println(accountInfo);
//    
    OpenOrders openOrders=tradeService.getOpenOrders();
    List<LimitOrder> limitOrders=openOrders.getOpenOrders();
    System.out.println(limitOrders);
    
    generic(tradeService);
//    raw((BitsoTradeServiceRaw) tradeService,tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

//    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal("16923.62"),
            new CurrencyPair(Currency.XRP, Currency.MXN),
            "",
            null,
            new BigDecimal("6.4998"));
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

//  private static void raw(BitsoTradeServiceRaw tradeServiceRaw,TradeService tradeService) throws IOException {
//
//    printRawOpenOrders(tradeServiceRaw);
//
//    // place a limit buy order
//    LimitOrder limitOrder =
//            new LimitOrder(
//                (OrderType.BID),
//                new BigDecimal("0.01"),
//                new CurrencyPair(Currency.BTC, Currency.USD),
//                "",
//                null,
//                new BigDecimal("5000.00"));
//    String limitOrderReturnValue  = tradeService.placeLimitOrder(limitOrder);
//    System.out.println("BitsoOrder return value: " + limitOrderReturnValue);
//
//    printRawOpenOrders(tradeServiceRaw);
//
//    // Cancel the added order
//    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
//    System.out.println("Canceling returned " + cancelResult);
//
//    printRawOpenOrders(tradeServiceRaw);
//  }
//
//  private static void printRawOpenOrders(BitsoTradeServiceRaw tradeService) throws IOException {
//
//	BitsoAllOrders openOrders = tradeService.getBitsoOpenOrders();
//    System.out.println("Open Orders: " + openOrders.getPayload().size());
//    for (Payload order : openOrders.getPayload()) {
//      System.out.println(order.toString());
//    }
//  }
}
