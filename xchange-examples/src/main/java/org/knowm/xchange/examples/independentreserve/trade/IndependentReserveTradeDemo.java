package org.knowm.xchange.examples.independentreserve.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.independentreserve.IndependentReserveDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

/** Author: Kamil Zbikowski Date: 4/14/15 */
public class IndependentReserveTradeDemo {
  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange independentReserve = IndependentReserveDemoUtils.createExchange();
    TradeService tradeService = independentReserve.getTradeService();

    generic(tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException, InterruptedException {
    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (Order.OrderType.ASK),
            new BigDecimal(".01"),
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("500.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);

    UserTrades tradeHistory = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println("Trade history: " + tradeHistory.toString());
  }

  static void printOpenOrders(TradeService tradeService) throws IOException, InterruptedException {
    // IR API caches data for some time, so we can get the same set of orders as we saw before
    TimeUnit.SECONDS.sleep(1);
    final OpenOrdersParamCurrencyPair params =
        (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    OpenOrders openOrders = tradeService.getOpenOrders(params);
    System.out.printf("All open Orders: %s%n", openOrders);

    params.setCurrencyPair(CurrencyPair.BTC_USD);
    openOrders = tradeService.getOpenOrders(params);
    System.out.printf("Open Orders for %s: %s%n: ", params, openOrders);

    params.setCurrencyPair(CurrencyPair.ETH_NZD);
    openOrders = tradeService.getOpenOrders(params);
    System.out.printf("Open Orders for %s: %s%n: ", params, openOrders);
  }
}
