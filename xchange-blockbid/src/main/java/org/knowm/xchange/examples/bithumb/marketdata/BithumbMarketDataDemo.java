package org.knowm.xchange.examples.bithumb.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.service.BithumbMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.examples.bithumb.BithumbDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BithumbMarketDataDemo {

  private static final CurrencyPair BTC_KRW = CurrencyPair.BTC_KRW;

  public static void main(String[] args) throws IOException {

    Exchange exchange = BithumbDemoUtils.createExchange();
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((BithumbMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC----------");
    System.out.println(marketDataService.getTicker(BTC_KRW));
    System.out.println(marketDataService.getTickers(null));
    System.out.println(marketDataService.getOrderBook(BTC_KRW));
    System.out.println(marketDataService.getTrades(BTC_KRW));
  }

  private static void raw(BithumbMarketDataServiceRaw marketDataServiceRaw) throws IOException {

    System.out.println("----------RAW----------");
    System.out.println(marketDataServiceRaw.getBithumbTicker(BTC_KRW));
    System.out.println(marketDataServiceRaw.getBithumbTickers());
    System.out.println(marketDataServiceRaw.getBithumbOrderBook(BTC_KRW));
    System.out.println(marketDataServiceRaw.getBithumbTrades(BTC_KRW));
  }
}
