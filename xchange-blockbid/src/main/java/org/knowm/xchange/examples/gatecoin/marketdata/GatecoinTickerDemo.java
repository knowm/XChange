package org.knowm.xchange.examples.gatecoin.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gatecoin.GatecoinExchange;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinTicker;
import org.knowm.xchange.gatecoin.service.GatecoinMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Ticker at Gatecoin. You can access both the raw data from Gatecoin or the
 * XChange generic DTO data format.
 */
public class GatecoinTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get gatecoin exchange API using default settings
    Exchange gatecoin = ExchangeFactory.INSTANCE.createExchange(GatecoinExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = gatecoin.getMarketDataService();

    generic(marketDataService);
    raw((GatecoinMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(GatecoinMarketDataServiceRaw marketDataService) throws IOException {

    GatecoinTicker[] gatecoinTicker = marketDataService.getGatecoinTicker().getTicker();

    System.out.println(gatecoinTicker[0].toString());
  }
}
