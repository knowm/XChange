package org.knowm.xchange.examples.cryptonit.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticatedV2.Side;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrder;
import org.knowm.xchange.cryptonit2.service.CryptonitTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.cryptonit.CryptonitDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Cryptonit exchange with authentication
 *   <li>Enter, review and cancel limit orders
 * </ul>
 */
public class CryptonitTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange cryptonit = CryptonitDemoUtils.createExchange();
    TradeService tradeService = cryptonit.getTradeService();

    generic(tradeService);
    raw((CryptonitTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {
    final OpenOrdersParamCurrencyPair openOrdersParamsBtcEur =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    openOrdersParamsBtcEur.setCurrencyPair(CurrencyPair.BTC_EUR);
    final OpenOrdersParamCurrencyPair openOrdersParamsBtcUsd =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    openOrdersParamsBtcUsd.setCurrencyPair(CurrencyPair.BTC_EUR);

    final OpenOrdersParams openOrdersParamsAll = tradeService.createOpenOrdersParams();

    printOpenOrders(tradeService, openOrdersParamsAll);

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.BID),
            new BigDecimal(".01"),
            CurrencyPair.BTC_EUR,
            null,
            null,
            new BigDecimal("500.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService, openOrdersParamsAll);
    printOpenOrders(tradeService, openOrdersParamsBtcEur);
    printOpenOrders(tradeService, openOrdersParamsBtcUsd);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService, openOrdersParamsAll);
  }

  private static void printOpenOrders(TradeService tradeService, OpenOrdersParams openOrdersParams)
      throws IOException {
    OpenOrders openOrders = tradeService.getOpenOrders(openOrdersParams);
    System.out.printf("Open Orders for %s: %s%n", openOrdersParams, openOrders);
  }

  private static void raw(CryptonitTradeServiceRaw tradeService) throws IOException {

    printRawOpenOrders(tradeService);

    // place a limit buy order
    CryptonitOrder order =
        tradeService.placeCryptonitOrder(
            CurrencyPair.BTC_EUR, Side.sell, new BigDecimal(".001"), new BigDecimal("1000.00"));
    System.out.println("CryptonitOrder return value: " + order);

    printRawOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelCryptonitOrder(order.getId());
    System.out.println("Canceling returned " + cancelResult);

    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(CryptonitTradeServiceRaw tradeService) throws IOException {

    CryptonitOrder[] openOrders = tradeService.getCryptonitOpenOrders(CurrencyPair.BTC_EUR);
    System.out.println("Open Orders: " + openOrders.length);
    for (CryptonitOrder order : openOrders) {
      System.out.println(order.toString());
    }
  }
}
