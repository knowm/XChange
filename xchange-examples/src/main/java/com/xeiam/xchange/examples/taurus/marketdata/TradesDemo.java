package com.xeiam.xchange.examples.taurus.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.taurus.Taurus;
import com.xeiam.xchange.taurus.TaurusExchange;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTransaction;
import com.xeiam.xchange.taurus.service.polling.TaurusMarketDataServiceRaw;

/**
 * Demonstrate requesting Trades at Taurus
 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get Taurus exchange API using default settings
    Exchange taurus = ExchangeFactory.INSTANCE.createExchange(TaurusExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = taurus.getPollingMarketDataService();

    generic(marketDataService);
    raw((TaurusMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {
    // Get the latest trade data for BTC/CAD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CAD);
    printTrades(trades.getTrades(), "default");

    trades = marketDataService.getTrades(CurrencyPair.BTC_CAD, Taurus.Time.hour);
    printTrades(trades.getTrades(), "hour");

    trades = marketDataService.getTrades(CurrencyPair.BTC_CAD, Taurus.Time.minute);
    printTrades(trades.getTrades(), "minute");
  }

  private static void raw(TaurusMarketDataServiceRaw marketDataService) throws IOException {
    // Get the latest trade data for BTC/CAD
    TaurusTransaction[] trades = marketDataService.getTaurusTransactions();
    printTrades(trades, "default");

    trades = marketDataService.getTaurusTransactions(Taurus.Time.hour);
    printTrades(trades, "hour");

    trades = marketDataService.getTaurusTransactions(Taurus.Time.minute);
    printTrades(trades, "minute");
  }

  private static <T> void printTrades(T[] trades, String x) {
    printTrades(Arrays.asList(trades), x);
  }

  private static <T> void printTrades(List<T> trades, String params) {
    System.out.printf("Trades (%s) - count: %d; first few:%n", params, trades.size());
    for (int i = 0; i < 5 && i < trades.size(); i++) {
      System.out.printf("%s%n", trades.get(i));
    }
  }
}
