package org.knowm.xchange.examples.bitcoinaverage;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcoinaverage.BitcoinAverageExchange;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import org.knowm.xchange.bitcoinaverage.service.BitcoinAverageMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitcoinAverageTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the BitcoinAverage exchange API using default settings
    Exchange bitcoinAverageExchange =
        ExchangeFactory.INSTANCE.createExchange(BitcoinAverageExchange.class.getName());
    bitcoinAverageExchange.remoteInit();
    generic(bitcoinAverageExchange);
    raw(bitcoinAverageExchange);
  }

  private static void generic(Exchange bitcoinAverageExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitcoinAverageExchange.getMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
    double value = ticker.getLast().doubleValue();

    System.out.println("Last: " + ticker.getCurrencyPair().counter.getCurrencyCode() + "-" + value);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
  }

  private static void raw(Exchange bitcoinAverageExchange) throws IOException {

    BitcoinAverageMarketDataServiceRaw bitcoinAverageMarketDataServiceRaw =
        (BitcoinAverageMarketDataServiceRaw) bitcoinAverageExchange.getMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    BitcoinAverageTicker ticker =
        bitcoinAverageMarketDataServiceRaw.getBitcoinAverageTicker("BTC", "EUR");

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Vol: " + ticker.getVolume());
  }
}
