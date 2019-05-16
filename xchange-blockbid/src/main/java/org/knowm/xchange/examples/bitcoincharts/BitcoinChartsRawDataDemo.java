package org.knowm.xchange.examples.bitcoincharts;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcoincharts.BitcoinChartsExchange;
import org.knowm.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import org.knowm.xchange.bitcoincharts.service.BitcoinChartsMarketDataService;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrates using the REST proxy to get the raw deserialized JSON object from BitcoinCharts
 *
 * @author timmolter
 */
public class BitcoinChartsRawDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BitcoinCharts exchange API using default settings
    Exchange bitcoinChartsExchange =
        ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());
    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitcoinChartsExchange.getMarketDataService();

    BitcoinChartsMarketDataService marketDataServiceRaw =
        (BitcoinChartsMarketDataService) marketDataService;

    BitcoinChartsTicker[] marketData = marketDataServiceRaw.getBitcoinChartsTickers();
    for (BitcoinChartsTicker data : marketData) {
      System.out.println(data.getSymbol() + ": " + data);
    }
  }
}
