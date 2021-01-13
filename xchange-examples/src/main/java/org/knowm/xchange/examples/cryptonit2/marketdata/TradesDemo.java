package org.knowm.xchange.examples.cryptonit2.marketdata;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptonit2.CryptonitExchange;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTransaction;
import org.knowm.xchange.cryptonit2.service.CryptonitMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Trades at Bitstamp */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitstamp exchange API using default settings
    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(CryptonitExchange.class);

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitstamp.getMarketDataService();

    generic(marketDataService);
    raw((CryptonitMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    System.out.println("Trades, default. Size= " + trades.getTrades().size());

    trades =
        marketDataService.getTrades(
            CurrencyPair.BTC_EUR, CryptonitMarketDataServiceRaw.CryptonitTime.HOUR);
    System.out.println("Trades, hour= " + trades.getTrades().size());

    trades =
        marketDataService.getTrades(
            CurrencyPair.BTC_EUR, CryptonitMarketDataServiceRaw.CryptonitTime.MINUTE);
    System.out.println("Trades, minute= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(CryptonitMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/USD
    CryptonitTransaction[] trades = marketDataService.getTransactions(CurrencyPair.BTC_EUR, null);
    System.out.println("Trades, default. Size= " + trades.length);

    trades =
        marketDataService.getTransactions(
            CurrencyPair.BTC_EUR, CryptonitMarketDataServiceRaw.CryptonitTime.HOUR);
    System.out.println("Trades, hour= " + trades.length);

    trades =
        marketDataService.getTransactions(
            CurrencyPair.BTC_EUR, CryptonitMarketDataServiceRaw.CryptonitTime.MINUTE);
    System.out.println("Trades, minute= " + trades.length);
    System.out.println(Arrays.toString(trades));
  }
}
