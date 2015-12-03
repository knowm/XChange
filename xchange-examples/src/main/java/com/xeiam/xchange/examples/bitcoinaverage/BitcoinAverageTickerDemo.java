package com.xeiam.xchange.examples.bitcoinaverage;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoinaverage.BitcoinAverageExchange;
import com.xeiam.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import com.xeiam.xchange.bitcoinaverage.service.polling.BitcoinAverageMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BitcoinAverageTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the BitcoinAverage exchange API using default settings
    Exchange bitcoinAverageExchange = ExchangeFactory.INSTANCE.createExchange(BitcoinAverageExchange.class.getName());
    bitcoinAverageExchange.remoteInit();
    generic(bitcoinAverageExchange);
    raw(bitcoinAverageExchange);
  }

  private static void generic(Exchange bitcoinAverageExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcoinAverageExchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
    double value = ticker.getLast().doubleValue();

    System.out.println("Last: " + ticker.getCurrencyPair().counter.getCurrencyCode() + "-" + value);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
  }

  private static void raw(Exchange bitcoinAverageExchange) throws IOException {

    BitcoinAverageMarketDataServiceRaw bitcoinAverageMarketDataServiceRaw = (BitcoinAverageMarketDataServiceRaw) bitcoinAverageExchange
        .getPollingMarketDataService();

    // Get the latest ticker data showing BTC to EUR
    BitcoinAverageTicker ticker = bitcoinAverageMarketDataServiceRaw.getBitcoinAverageTicker("BTC", "EUR");

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Vol: " + ticker.getVolume());
  }
}
