package org.knowm.xchange.examples.btcturk.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.service.BTCTurkMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.btcturk.BTCTurkDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author semihunaldi Demonstrate requesting Ticker at BTCTurk. You can access both the raw data
 *     from BTCTurk or the XChange generic DTO data format.
 */
public class BTCTurkTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTCTurk exchange API using default settings
    Exchange btcTurk = BTCTurkDemoUtils.createExchange();

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = btcTurk.getMarketDataService();

    generic(marketDataService);
    raw((BTCTurkMarketDataService) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_TRY);
    System.out.println(ticker.toString());
  }

  private static void raw(BTCTurkMarketDataService marketDataService) throws IOException {
    BTCTurkTicker btcTurkTicker = marketDataService.getBTCTurkTicker(CurrencyPair.BTC_TRY);
    System.out.println(btcTurkTicker.toString());
  }
}
