package org.knowm.xchange.examples.koineks.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.koineks.KoineksDemoUtils;
import org.knowm.xchange.koineks.dto.marketdata.KoineksTicker;
import org.knowm.xchange.koineks.service.KoineksMarketDataService;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author semihunaldi Demonstrate requesting Ticker at Koineks. You can access both the raw data
 *     from Koineks or the XChange generic DTO data format.
 */
public class KoineksTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Koineks exchange API using default settings
    Exchange koineks = KoineksDemoUtils.createExchange();

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = koineks.getMarketDataService();

    generic(marketDataService);
    raw((KoineksMarketDataService) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    Ticker btcTicker = marketDataService.getTicker(CurrencyPair.BTC_TRY);
    Ticker ethTicker = marketDataService.getTicker(CurrencyPair.ETH_TRY);
    Ticker ltcTicker = marketDataService.getTicker(CurrencyPair.LTC_TRY);
    Ticker dogeTicker = marketDataService.getTicker(CurrencyPair.DOGE_TRY);
    Ticker dashTicker = marketDataService.getTicker(CurrencyPair.DASH_TRY);
    System.out.println(btcTicker);
    System.out.println(ethTicker);
    System.out.println(ltcTicker);
    System.out.println(dogeTicker);
    System.out.println(dashTicker);
  }

  private static void raw(KoineksMarketDataService marketDataService) throws IOException {
    KoineksTicker koineksTicker = marketDataService.getKoineksTicker();
    System.out.println(koineksTicker.toString());
  }
}
