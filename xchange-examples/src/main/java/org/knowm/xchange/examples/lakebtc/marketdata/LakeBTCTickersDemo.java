package org.knowm.xchange.examples.lakebtc.marketdata;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import org.knowm.xchange.lakebtc.LakeBTCAdapters;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import org.knowm.xchange.lakebtc.service.LakeBTCMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Created by Cristi on 12/22/2014. */
public class LakeBTCTickersDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    MarketDataService marketDataService = lakebtcExchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.USD);
    System.out.println("Last: " + ticker.getLast());
    System.out.println("Volume: " + ticker.getVolume());
    System.out.println("High: " + ticker.getHigh());
    System.out.println("Low: " + ticker.getLow());

    ticker = marketDataService.getTicker(CurrencyPair.BTC_HKD);
    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.HKD);
    System.out.println("Last: " + ticker.getLast());
    System.out.println("Volume: " + ticker.getVolume());
    System.out.println("High: " + ticker.getHigh());
    System.out.println("Low: " + ticker.getLow());
  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCMarketDataServiceRaw marketDataService =
        (LakeBTCMarketDataServiceRaw) lakeBtcExchange.getMarketDataService();
    Map<String, LakeBTCTicker> tickers = marketDataService.getLakeBTCTickers();

    LakeBTCTicker hkd = tickers.get(LakeBTCAdapters.adaptCurrencyPair(CurrencyPair.BTC_HKD));
    System.out.println("Ticker: " + hkd);
    System.out.println("Currency: " + Currency.HKD);
    System.out.println("Last: " + hkd.getLast());
    System.out.println("Volume: " + hkd.getVolume());
    System.out.println("High: " + hkd.getHigh());
    System.out.println("Low: " + hkd.getLow());

    LakeBTCTicker usd = tickers.get(LakeBTCAdapters.adaptCurrencyPair(CurrencyPair.BTC_USD));
    System.out.println("Ticker: " + usd);
    System.out.println("Currency: " + Currency.USD);
    System.out.println("Last: " + usd.getLast());
    System.out.println("Volume: " + usd.getVolume());
    System.out.println("High: " + usd.getHigh());
    System.out.println("Low: " + usd.getLow());
  }
}
