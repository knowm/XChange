package org.knowm.xchange.examples.anx.v2.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.anx.v2.ANXExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class ANXTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get ANX exchange API using default settings
    Exchange anx = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = anx.getMarketDataService();

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
