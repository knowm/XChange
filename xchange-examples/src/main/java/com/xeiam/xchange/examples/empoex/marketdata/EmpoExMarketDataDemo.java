package com.xeiam.xchange.examples.empoex.marketdata;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.empoex.service.polling.EmpoExMarketDataServiceRaw;
import com.xeiam.xchange.examples.empoex.EmpoExDemoUtils;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class EmpoExMarketDataDemo {

  private static CurrencyPair currencyPair = new CurrencyPair("FAIL", "LTC");

  public static void main(String[] args) throws IOException {

    PollingMarketDataService dataService = EmpoExDemoUtils.getExchange().getPollingMarketDataService();

    generic(dataService);
    raw((EmpoExMarketDataServiceRaw) dataService);
  }

  public static void generic(PollingMarketDataService dataService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    System.out.println(dataService.getTicker(currencyPair));
    System.out.println(dataService.getTrades(currencyPair));
    System.out.println(dataService.getOrderBook(currencyPair));
  }

  public static void raw(EmpoExMarketDataServiceRaw dataService) throws IOException {

    System.out.println(dataService.getEmpoExTicker(currencyPair));
    System.out.println(dataService.getEmpoExTrades(currencyPair));
    System.out.println(dataService.getEmpoExDepth(currencyPair));
  }
}
