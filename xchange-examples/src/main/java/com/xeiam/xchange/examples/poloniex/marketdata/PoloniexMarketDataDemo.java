package com.xeiam.xchange.examples.poloniex.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.poloniex.PoloniexExchange;
import com.xeiam.xchange.poloniex.service.polling.PoloniexMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
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
    currencyPair = new CurrencyPair(Currency.XMR, Currency.BTC);

    generic(dataService);
    raw((PoloniexMarketDataServiceRaw) dataService);
  }

  private static void generic(PollingMarketDataService dataService) throws IOException {

    System.out.println("----------GENERIC----------");
    System.out.println(dataService.getExchangeSymbols());
    System.out.println(dataService.getTicker(currencyPair));
    System.out.println(dataService.getOrderBook(currencyPair));
    System.out.println(dataService.getOrderBook(currencyPair, 3));
    System.out.println(dataService.getTrades(currencyPair));
    long now = new Date().getTime() / 1000;
    System.out.println(dataService.getTrades(currencyPair, now - 8 * 60 * 60, now));
  }

  private static void raw(PoloniexMarketDataServiceRaw dataService) throws IOException {

    System.out.println("------------RAW------------");
    System.out.println(dataService.getPoloniexCurrencyInfo());
    System.out.println(dataService.getExchangeSymbols());
    System.out.println(dataService.getAllPoloniexTickers());
    System.out.println(dataService.getPoloniexTicker(currencyPair));
    System.out.println(dataService.getAllPoloniexDepths());
    System.out.println(dataService.getAllPoloniexDepths(3));
    System.out.println(dataService.getPoloniexDepth(currencyPair));
    System.out.println(dataService.getPoloniexDepth(currencyPair, 3));
    System.out.println(Arrays.asList(dataService.getPoloniexPublicTrades(currencyPair)));
    long now = new Date().getTime() / 1000;
    System.out.println(Arrays.asList(dataService.getPoloniexPublicTrades(currencyPair, now - 8 * 60 * 60, null)));
  }

}
