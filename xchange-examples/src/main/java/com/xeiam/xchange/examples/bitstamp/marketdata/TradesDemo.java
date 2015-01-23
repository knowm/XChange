package com.xeiam.xchange.examples.bitstamp.marketdata;

import java.io.IOException;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitstamp.BitstampExchange;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import com.xeiam.xchange.bitstamp.service.polling.BitstampMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Trades at Bitstamp
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitstamp exchange API using default settings
    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitstamp.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitstampMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println("Trades, default. Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, BitstampMarketDataServiceRaw.BitstampTime.HOUR);
    System.out.println("Trades, hour= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, BitstampMarketDataServiceRaw.BitstampTime.MINUTE);
    System.out.println("Trades, minute= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(BitstampMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/USD
    BitstampTransaction[] trades = marketDataService.getBitstampTransactions();
    System.out.println("Trades, default. Size= " + trades.length);

    trades = marketDataService.getBitstampTransactions(BitstampMarketDataServiceRaw.BitstampTime.HOUR);
    System.out.println("Trades, hour= " + trades.length);

    trades = marketDataService.getBitstampTransactions(BitstampMarketDataServiceRaw.BitstampTime.MINUTE);
    System.out.println("Trades, minute= " + trades.length);
    System.out.println(Arrays.toString(trades));
  }
}
