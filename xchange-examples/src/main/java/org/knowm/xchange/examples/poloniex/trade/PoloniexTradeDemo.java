package org.knowm.xchange.examples.poloniex.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.poloniex.PoloniexExamplesUtils;
import org.knowm.xchange.poloniex.PoloniexAdapters;
import org.knowm.xchange.poloniex.service.PoloniexTradeService;
import org.knowm.xchange.poloniex.service.PoloniexTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.utils.CertHelper;

/**
 * @author Zach Holmes
 */

public class PoloniexTradeDemo {

  private static final CurrencyPair REP_ETH = new CurrencyPair("REP", "ETH");

  private static CurrencyPair currencyPair;
  private static BigDecimal xmrBuyRate;

  public static void main(String[] args) throws Exception {
    CertHelper.trustAllCerts();

    Exchange poloniex = PoloniexExamplesUtils.getExchange();
    TradeService tradeService = poloniex.getTradeService();
    currencyPair = new CurrencyPair(Currency.XMR, Currency.BTC);

    /*
     * Make sure this is below the current market rate!!
     */
    xmrBuyRate = new BigDecimal("0.003");

    generic(tradeService);
    raw((PoloniexTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws Exception {
    System.out.println("----------GENERIC----------");

    PoloniexTradeService.PoloniexTradeHistoryParams params = new PoloniexTradeService.PoloniexTradeHistoryParams();
    params.setCurrencyPair(currencyPair);
    System.out.println(tradeService.getTradeHistory(params));

    params.setStartTime(new Date());
    System.out.println(tradeService.getTradeHistory(params));

    Calendar endTime = Calendar.getInstance();
    endTime.add(Calendar.HOUR, 4);
    params.setEndTime(endTime.getTime());
    System.out.println(tradeService.getTradeHistory(params));

    LimitOrder order = new LimitOrder.Builder(OrderType.BID, currencyPair).tradableAmount(new BigDecimal(".1")).limitPrice(xmrBuyRate).build();
    String orderId = tradeService.placeLimitOrder(order);
    System.out.println("Placed order #" + orderId);

    printOpenOrders(tradeService);

    boolean canceled = tradeService.cancelOrder(orderId);
    if (canceled) {
      System.out.println("Successfully canceled order #" + orderId);
    } else {
      System.out.println("Did not successfully cancel order #" + orderId);
    }

    printOpenOrders(tradeService);
  }

  private static void raw(PoloniexTradeServiceRaw tradeService) throws IOException, InterruptedException {
    System.out.println("------------RAW------------");
    System.out.println(Arrays.asList(tradeService.returnTradeHistory(currencyPair, null, null)));
    long startTime = (new Date().getTime() / 1000) - 8 * 60 * 60;
    System.out.println(Arrays.asList(tradeService.returnTradeHistory(currencyPair, startTime, null)));
    long endTime = new Date().getTime() / 1000;
    System.out.println(Arrays.asList(tradeService.returnTradeHistory(currencyPair, startTime, endTime)));

    LimitOrder order = new LimitOrder.Builder(OrderType.BID, currencyPair).tradableAmount(new BigDecimal("1")).limitPrice(xmrBuyRate).build();
    String orderId = tradeService.buy(order).getOrderNumber().toString();
    System.out.println("Placed order #" + orderId);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println(PoloniexAdapters.adaptPoloniexOpenOrders(tradeService.returnOpenOrders()));

    boolean canceled = tradeService.cancel(orderId);
    if (canceled) {
      System.out.println("Successfully canceled order #" + orderId);
    } else {
      System.out.println("Did not successfully cancel order #" + orderId);
    }

    Thread.sleep(3000); // wait for cancellation to propagate

    System.out.println(PoloniexAdapters.adaptPoloniexOpenOrders(tradeService.returnOpenOrders()));
  }

  private static void printOpenOrders(TradeService tradeService) throws Exception {
    TimeUnit.SECONDS.sleep(2);

    final OpenOrdersParamCurrencyPair params = (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    OpenOrders openOrders = tradeService.getOpenOrders(params);
    System.out.printf("All open Orders: %s%n", openOrders);

    params.setCurrencyPair(currencyPair);
    openOrders = tradeService.getOpenOrders(params);
    System.out.printf("Open Orders for %s: %s%n: ", params, openOrders);

    params.setCurrencyPair(REP_ETH);
    openOrders = tradeService.getOpenOrders(params);
    System.out.printf("Open Orders for %s: %s%n: ", params, openOrders);
  }
}
