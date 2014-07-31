package com.xeiam.xchange.examples.poloniex.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.poloniex.PoloniexExchange;
import com.xeiam.xchange.poloniex.service.polling.PoloniexMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.CertHelper;

/**
 * @author Zach Holmes
 */

public class PoloniexMarketDataDemo {

  private static CurrencyPair currencyPair;

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange poloniex = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    PollingMarketDataService dataService = poloniex.getPollingMarketDataService();
    currencyPair = new CurrencyPair("XMR", Currencies.BTC);

    generic(dataService);
    raw((PoloniexMarketDataServiceRaw) dataService);
  }

  private static void generic(PollingMarketDataService dataService) throws IOException {

    System.out.println("----------GENERIC----------");
    System.out.println(dataService.getExchangeSymbols());
    System.out.println(dataService.getTicker(currencyPair));
    System.out.println(dataService.getOrderBook(currencyPair));
    System.out.println(dataService.getTrades(currencyPair));
  }

  private static void raw(PoloniexMarketDataServiceRaw dataService) throws IOException {

    System.out.println("------------RAW------------");
    System.out.println(dataService.getExchangeSymbols());
    System.out.println(dataService.getAllPoloniexTickers());
    System.out.println(dataService.getPoloniexTicker(currencyPair));
    System.out.println(dataService.getAllPoloniexDepths());
    System.out.println(dataService.getPoloniexDepth(currencyPair));
    System.out.println(dataService.getPoloniexPublicTrades(currencyPair));
  }

}
