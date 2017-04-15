package org.knowm.xchange.examples.bitcoincharts;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcoincharts.BitcoinChartsExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demo requesting polling Ticker at BitcoinCharts
 *
 * @author timmolter
 */
public class BitcoinChartsTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BitcoinCharts exchange API using default settings
    Exchange bitcoinChartsExchange = ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitcoinChartsExchange.getMarketDataService();

    // Get the latest ticker data showing BTC/bitstampUSD
    CurrencyPair currencyPair = new CurrencyPair("BTC", "bitstampUSD");
    Ticker ticker = marketDataService.getTicker(currencyPair);

    double value = ticker.getLast().doubleValue();

    String currency = ticker.getCurrencyPair().counter.getCurrencyCode();
    System.out.println("bitstampUSD Last: " + currency + "-" + value);
    System.out.println("bitstampUSD Last: " + ticker.getLast().toString());

  }
}
