package org.knowm.xchange.examples.paribu.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.paribu.ParibuDemoUtils;
import org.knowm.xchange.paribu.dto.marketdata.ParibuTicker;
import org.knowm.xchange.paribu.service.ParibuMarketDataService;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author semihunaldi Demonstrate requesting Ticker at Paribu. You can access both the raw data
 *     from Paribu or the XChange generic DTO data format.
 */
public class ParibuTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Paribu exchange API using default settings
    Exchange paribu = ParibuDemoUtils.createExchange();

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = paribu.getMarketDataService();

    generic(marketDataService);
    raw((ParibuMarketDataService) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_TRY);
    System.out.println(ticker.toString());
  }

  private static void raw(ParibuMarketDataService marketDataService) throws IOException {
    ParibuTicker paribuTicker = marketDataService.getParibuTicker();
    System.out.println(paribuTicker.toString());
  }
}
