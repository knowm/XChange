package com.xeiam.xchange.examples.taurus.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.taurus.TaurusExchange;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTicker;
import com.xeiam.xchange.taurus.service.polling.TaurusMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Taurus. You can access both the raw data from Taurus or the XChange generic DTO data format.
 */
public class TaurusTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Taurus exchange API using default settings
    Exchange taurus = ExchangeFactory.INSTANCE.createExchange(TaurusExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = taurus.getPollingMarketDataService();

    generic(marketDataService);
    raw((TaurusMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_CAD);

    System.out.println(ticker.toString());
  }

  private static void raw(TaurusMarketDataServiceRaw marketDataService) throws IOException {

    TaurusTicker taurusTicker = marketDataService.getTaurusTicker();

    System.out.println(taurusTicker.toString());
  }

}
