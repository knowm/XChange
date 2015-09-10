package com.xeiam.xchange.examples.core.utils;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitso.BitsoExchange;
import com.xeiam.xchange.bitso.dto.BitsoException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.utils.retries.IPredicate;
import com.xeiam.xchange.utils.retries.Retries;

public class RetriesDemo {
  public static final IPredicate<Exception> TOO_FREQUENT_REQUESTS = new IPredicate<Exception>() {
    @Override
    public boolean test(Exception e) {
      return e.getMessage().contains("{code=200, message=Too many requests}");
    }
  };

  public static void main(String[] args) throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitsoExchange.class.getName());
    PollingMarketDataService service = exchange.getPollingMarketDataService();
    CurrencyPair cp = new CurrencyPair("BTC", "MXN");

    try {
      getMarketDataTooFastWithoutRetries(service, cp);
    } catch (BitsoException e) {
      System.out.println(e);
    }

    System.out.println("======================");

    getMarketDataTooFastWithRetries(service, cp);

  }

  private static void getMarketDataTooFastWithoutRetries(PollingMarketDataService service, CurrencyPair cp)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    System.out.println("Polling for orderbooks too fast without using Retries:");
    for (int i = 0; i < 50; i++) {
      OrderBook ob = service.getOrderBook(cp);
      System.out.println(ob);

    }
  }

  private static void getMarketDataTooFastWithRetries(final PollingMarketDataService service, final CurrencyPair cp) throws Exception {

    System.out.println("Polling for orderbooks too fast using Retries:");
    final Callable<OrderBook> orderBookAction = new Callable<OrderBook>() {
      @Override
      public OrderBook call() throws Exception {
        return service.getOrderBook(cp);
      }
    };

    for (int i = 0; i < 50; i++) {
      OrderBook ob = Retries.callWithRetries(10, 1, orderBookAction, TOO_FREQUENT_REQUESTS);
      /*
       * Use Java 8 lambda expression to avoid all the boilerplate code with a Callable: OrderBook ob = Retries.callWithRetries(10, 1, () ->
       * service.getOrderBook(cp), TOO_FREQUENT_REQUESTS);
       */
      System.out.println(ob);

    }
  }

}
