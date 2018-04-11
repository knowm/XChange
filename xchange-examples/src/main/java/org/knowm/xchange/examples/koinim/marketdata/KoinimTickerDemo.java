package org.knowm.xchange.examples.koinim.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.koinim.KoinimDemoUtils;
import org.knowm.xchange.koinim.dto.marketdata.KoinimTicker;
import org.knowm.xchange.koinim.service.KoinimMarketDataService;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author ahmetoz Demonstrate requesting Ticker at www.koinim.com. You can access both the raw data
 *     from Koinim or the XChange generic DTO data format.
 */
public class KoinimTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Koinim exchange API using default settings
    Exchange koinim = KoinimDemoUtils.createExchange();

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = koinim.getMarketDataService();

    generic(marketDataService);
    raw((KoinimMarketDataService) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_TRY);
    System.out.println(ticker.toString());
  }

  private static void raw(KoinimMarketDataService marketDataService) throws IOException {
    KoinimTicker koinimTicker = marketDataService.getKoinimTicker();
    System.out.println(koinimTicker.toString());
  }
}
