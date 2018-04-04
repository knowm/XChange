package org.knowm.xchange.examples.poloniex.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Zach Holmes */
public class PoloniexMarketDataDemo {

  private static Exchange poloniex;
  private static CurrencyPair currencyPair;

  public static void main(String[] args) throws Exception {

    //    CertHelper.trustAllCerts();

    poloniex = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    MarketDataService dataService = poloniex.getMarketDataService();
    currencyPair = new CurrencyPair("BTC", "USDT");
    //    currencyPair = new CurrencyPair("ETH", "BTC");

    generic(dataService);
    //    raw((PoloniexMarketDataServiceRaw) dataService);
  }

  private static void generic(MarketDataService dataService) throws IOException {

    System.out.println("----------GENERIC----------");
    System.out.println(dataService.getTicker(currencyPair));
    System.out.println(dataService.getOrderBook(currencyPair));
    //    System.out.println(dataService.getOrderBook(currencyPair, 3));
    //    System.out.println(dataService.getTrades(currencyPair));
    //    long now = new Date().getTime() / 1000;
    //    System.out.println(dataService.getTrades(currencyPair, now -  60));
  }

  private static void raw(PoloniexMarketDataServiceRaw dataService) throws IOException {

    System.out.println("------------RAW------------");
    System.out.println(dataService.getPoloniexCurrencyInfo());
    System.out.println(poloniex.getExchangeSymbols());
    System.out.println(dataService.getAllPoloniexTickers());
    System.out.println(dataService.getPoloniexTicker(currencyPair));
    System.out.println(dataService.getAllPoloniexDepths());
    System.out.println(dataService.getAllPoloniexDepths(3));
    System.out.println(dataService.getPoloniexDepth(currencyPair));
    System.out.println(dataService.getPoloniexDepth(currencyPair, 3));
    System.out.println(Arrays.asList(dataService.getPoloniexPublicTrades(currencyPair)));
    long now = new Date().getTime() / 1000;
    System.out.println(
        Arrays.asList(dataService.getPoloniexPublicTrades(currencyPair, now - 8 * 60 * 60, null)));
  }
}
