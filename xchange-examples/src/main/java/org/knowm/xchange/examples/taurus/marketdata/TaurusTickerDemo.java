package org.knowm.xchange.examples.taurus.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.taurus.TaurusExchange;
import org.knowm.xchange.taurus.dto.marketdata.TaurusTicker;
import org.knowm.xchange.taurus.service.TaurusMarketDataServiceRaw;

/**
 * Demonstrate requesting Ticker at Taurus. You can access both the raw data from Taurus or the XChange generic DTO data format.
 */
public class TaurusTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Taurus exchange API using default settings
    Exchange taurus = ExchangeFactory.INSTANCE.createExchange(TaurusExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = taurus.getMarketDataService();

    generic(marketDataService);
    raw((TaurusMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_CAD);

    System.out.println(ticker.toString());
  }

  private static void raw(TaurusMarketDataServiceRaw marketDataService) throws IOException {

    TaurusTicker taurusTicker = marketDataService.getTaurusTicker();

    System.out.println(taurusTicker.toString());
  }

}
