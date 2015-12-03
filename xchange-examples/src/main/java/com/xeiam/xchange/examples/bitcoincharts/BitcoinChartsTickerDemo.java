package com.xeiam.xchange.examples.bitcoincharts;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demo requesting polling Ticker at BitcoinCharts
 * 
 * @author timmolter
 */
public class BitcoinChartsTickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BitcoinCharts exchange API using default settings
    Exchange bitcoinChartsExchange = ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcoinChartsExchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC/bitstampUSD
    CurrencyPair currencyPair = new CurrencyPair("BTC", "bitstampUSD");
    Ticker ticker = marketDataService.getTicker(currencyPair);

    double value = ticker.getLast().doubleValue();

    String currency = ticker.getCurrencyPair().counter.getCurrencyCode().toString();
    System.out.println("bitstampUSD Last: " + currency + "-" + value);
    System.out.println("bitstampUSD Last: " + ticker.getLast().toString());

  }
}
