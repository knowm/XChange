package com.xeiam.xchange.examples.lakebtc.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Created by Cristi on 12/22/2014.
 */
public class LakeBTCTickersDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    PollingMarketDataService marketDataService = lakebtcExchange.getPollingMarketDataService();
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.USD);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());

    ticker = marketDataService.getTicker(CurrencyPair.BTC_CNY);
    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.CNY);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());

  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCMarketDataServiceRaw marketDataService = (LakeBTCMarketDataServiceRaw) lakeBtcExchange.getPollingMarketDataService();
    LakeBTCTickers tickers = marketDataService.getLakeBTCTickers();

    System.out.println("Ticker: " + tickers.getCny().toString());
    System.out.println("Currency: " + Currency.CNY);
    System.out.println("Last: " + tickers.getCny().getLast().toString());
    System.out.println("Volume: " + tickers.getCny().getVolume().toString());
    System.out.println("High: " + tickers.getCny().getHigh().toString());
    System.out.println("Low: " + tickers.getCny().getLow().toString());

    System.out.println("Ticker: " + tickers.getUsd().toString());
    System.out.println("Currency: " + Currency.USD);
    System.out.println("Last: " + tickers.getUsd().getLast().toString());
    System.out.println("Volume: " + tickers.getUsd().getVolume().toString());
    System.out.println("High: " + tickers.getUsd().getHigh().toString());
    System.out.println("Low: " + tickers.getUsd().getLow().toString());

  }
}
