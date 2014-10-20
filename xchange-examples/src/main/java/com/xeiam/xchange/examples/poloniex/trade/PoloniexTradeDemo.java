package com.xeiam.xchange.examples.poloniex.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.poloniex.PoloniexExamplesUtils;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.service.polling.PoloniexTradeServiceRaw;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.utils.CertHelper;

/**
 * @author Zach Holmes
 */

public class PoloniexTradeDemo {

  private static CurrencyPair currencyPair;
  private static BigDecimal xmrBuyRate;

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange poloniex = PoloniexExamplesUtils.getExchange();
    PollingTradeService tradeService = poloniex.getPollingTradeService();
    currencyPair = new CurrencyPair("XMR", Currencies.BTC);

    /*
     * Make sure this is below the current market rate!!
     */
    xmrBuyRate = new BigDecimal("0.003");

    generic(tradeService);
    raw((PoloniexTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException, InterruptedException {

    System.out.println("----------GENERIC----------");

    System.out.println(tradeService.getTradeHistory(currencyPair));
    long startTime = (new Date().getTime() / 1000) - 8 * 60 * 60;
    System.out.println(tradeService.getTradeHistory(currencyPair, startTime));
    long endTime = startTime + 4 * 60 * 60;
    System.out.println(tradeService.getTradeHistory(currencyPair, startTime, endTime));

    LimitOrder order = new LimitOrder.Builder(OrderType.BID, currencyPair).setTradableAmount(new BigDecimal(".01")).setLimitPrice(xmrBuyRate).build();
    String orderId = tradeService.placeLimitOrder(order);
    System.out.println("Placed order #" + orderId);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println(tradeService.getOpenOrders());

    boolean canceled = tradeService.cancelOrder(orderId);
    if (canceled) {
      System.out.println("Successfully canceled order #" + orderId);
    }
    else {
      System.out.println("Did not successfully cancel order #" + orderId);
    }

    Thread.sleep(3000); // wait for cancellation to propagate

    System.out.println(tradeService.getOpenOrders());
  }

  private static void raw(PoloniexTradeServiceRaw tradeService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException, InterruptedException {

    System.out.println("------------RAW------------");
    System.out.println(Arrays.asList(tradeService.returnTradeHistory(currencyPair)));
    long startTime = (new Date().getTime() / 1000) - 8 * 60 * 60;
    System.out.println(Arrays.asList(tradeService.returnTradeHistory(currencyPair, startTime, null)));
    long endTime = new Date().getTime() / 1000;
    System.out.println(Arrays.asList(tradeService.returnTradeHistory(currencyPair, startTime, endTime)));

    LimitOrder order = new LimitOrder.Builder(OrderType.BID, currencyPair).setTradableAmount(new BigDecimal("1")).setLimitPrice(xmrBuyRate).build();
    String orderId = tradeService.buy(order);
    System.out.println("Placed order #" + orderId);

    Thread.sleep(3000); // wait for order to propagate

    System.out.println(PoloniexAdapters.adaptPoloniexOpenOrders(tradeService.returnOpenOrders()));

    boolean canceled = tradeService.cancel(orderId);
    if (canceled) {
      System.out.println("Successfully canceled order #" + orderId);
    }
    else {
      System.out.println("Did not successfully cancel order #" + orderId);
    }

    Thread.sleep(3000); // wait for cancellation to propagate

    System.out.println(PoloniexAdapters.adaptPoloniexOpenOrders(tradeService.returnOpenOrders()));
  }
}
