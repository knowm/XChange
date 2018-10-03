package org.knowm.xchange.examples.gatecoin.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.gatecoin.GatecoinExchange;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinTransaction;
import org.knowm.xchange.gatecoin.service.GatecoinMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author sumdeha */
public class GatecoinTradesDemo {
  public static void main(String[] args) throws IOException {

    // Use the factory to get gatecoin exchange API using default settings
    Exchange gatecoin = ExchangeFactory.INSTANCE.createExchange(GatecoinExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = gatecoin.getMarketDataService();

    generic(marketDataService);
    raw((GatecoinMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades.getTrades().toString());

    Trades tradesWithCount = marketDataService.getTrades(CurrencyPair.BTC_EUR, 10);
    System.out.println(tradesWithCount.getTrades().toString());

    Trades tradesWithCountAndTxId =
        marketDataService.getTrades(CurrencyPair.BTC_HKD, 5, (long) 1386153);
    System.out.println(tradesWithCountAndTxId.getTrades().toString());
  }

  private static void raw(GatecoinMarketDataServiceRaw marketDataService) throws IOException {

    GatecoinTransaction[] gatecoinTransactions =
        marketDataService
            .getGatecoinTransactions(CurrencyPair.BTC_USD.toString())
            .getTransactions();

    System.out.println(gatecoinTransactions[0].toString());
  }
}
