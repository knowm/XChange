package com.xeiam.xchange.examples.clevercoin.marketdata;

import java.io.IOException;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.clevercoin.CleverCoinExchange;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinTransaction;
import com.xeiam.xchange.clevercoin.service.polling.CleverCoinMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Trades at CleverCoin
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get CleverCoin exchange API using default settings
    Exchange clevercoin = ExchangeFactory.INSTANCE.createExchange(CleverCoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = clevercoin.getPollingMarketDataService();

    generic(marketDataService);
    raw((CleverCoinMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/EUR
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    System.out.println("Trades, default. Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    System.out.println("Trades, hour= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    System.out.println("Trades, minute= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(CleverCoinMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/EUR
	CleverCoinTransaction[] trades = marketDataService.getCleverCoinTransactions();
    System.out.println("Trades, default. Size= " + trades.length);

    trades = marketDataService.getCleverCoinTransactions();
    System.out.println("Trades, hour= " + trades.length);

    trades = marketDataService.getCleverCoinTransactions();
    System.out.println("Trades, minute= " + trades.length);
    System.out.println(Arrays.toString(trades));
  }
}
