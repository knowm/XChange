package com.xeiam.xchange.gatecoin.testclient.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.gatecoin.GatecoinExchange;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinTicker;
import com.xeiam.xchange.gatecoin.service.polling.GatecoinMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Gatecoin. You can access both the raw data from Gatecoin or the XChange generic DTO data format.
 */
public class GatecoinTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get gatecoin exchange API using default settings
    Exchange gatecoin = ExchangeFactory.INSTANCE.createExchange(GatecoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = gatecoin.getPollingMarketDataService();

    generic(marketDataService);
    raw((GatecoinMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(GatecoinMarketDataServiceRaw marketDataService) throws IOException {

    GatecoinTicker[] gatecoinTicker = marketDataService.getGatecoinTicker().getTicker();

    System.out.println(gatecoinTicker[0].toString());
  }

}
