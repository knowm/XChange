package org.knowm.xchange.examples.cryptonit.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptonit2.CryptonitExchange;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTicker;
import org.knowm.xchange.cryptonit2.service.CryptonitMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Ticker at Bitstamp. You can access both the raw data from Bitstamp or the
 * XChange generic DTO data format.
 */
public class CryptonitTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitstamp exchange API using default settings
    Exchange cryptonit = ExchangeFactory.INSTANCE.createExchange(CryptonitExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = cryptonit.getMarketDataService();

    generic(marketDataService);
    raw((CryptonitMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);

    System.out.println(ticker.toString());
  }

  private static void raw(CryptonitMarketDataServiceRaw marketDataService) throws IOException {

    CryptonitTicker bitstampTicker = marketDataService.getCryptonitTicker(CurrencyPair.BTC_EUR);

    System.out.println(bitstampTicker.toString());
  }
}
