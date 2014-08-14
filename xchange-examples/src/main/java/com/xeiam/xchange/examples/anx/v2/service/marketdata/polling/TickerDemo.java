package com.xeiam.xchange.examples.anx.v2.service.marketdata.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Test requesting polling Ticker at MtGox
 */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the version 2 MtGox exchange API using default settings
    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = anx.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());

    // Get the latest ticker data showing BTC to EUR
    ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
    System.out.println(ticker.toString());

    // Get the latest ticker data showing BTC to GBP
    ticker = marketDataService.getTicker(CurrencyPair.BTC_GBP);
    System.out.println(ticker.toString());

  }

}
