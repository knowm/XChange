package org.knowm.xchange.examples.bitstamp.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.service.polling.BitstampMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Bitstamp. You can access both the raw data from Bitstamp or the XChange generic DTO data format.
 */
public class BitstampTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitstamp exchange API using default settings
    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitstamp.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitstampMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(BitstampMarketDataServiceRaw marketDataService) throws IOException {

    BitstampTicker bitstampTicker = marketDataService.getBitstampTicker();

    System.out.println(bitstampTicker.toString());
  }

}
