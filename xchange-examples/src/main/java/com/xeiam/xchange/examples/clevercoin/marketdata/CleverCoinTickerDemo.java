package com.xeiam.xchange.examples.clevercoin.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.clevercoin.CleverCoinExchange;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinTicker;
import com.xeiam.xchange.clevercoin.service.polling.CleverCoinMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at CleverCoin. You can access both the raw data from CleverCoin or the XChange generic DTO data format.
 */
public class CleverCoinTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get CleverCoin exchange API using default settings
    Exchange clevercoin = ExchangeFactory.INSTANCE.createExchange(CleverCoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = clevercoin.getPollingMarketDataService();

    generic(marketDataService);
    raw((CleverCoinMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);

    System.out.println(ticker.toString());
  }

  private static void raw(CleverCoinMarketDataServiceRaw marketDataService) throws IOException {

    CleverCoinTicker cleverCoinTicker = marketDataService.getCleverCoinTicker();

    System.out.println(cleverCoinTicker.toString());
  }

}
