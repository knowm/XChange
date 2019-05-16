package org.knowm.xchange.examples.cryptopia.marketdata;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;
import org.knowm.xchange.cryptopia.service.CryptopiaMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Trades at Cryptopia */
public class TradesDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get Cryptopia exchange API using default settings
    Exchange cryptopia = ExchangeFactory.INSTANCE.createExchange(CryptopiaExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = cryptopia.getMarketDataService();

    generic(marketDataService);
    raw((CryptopiaMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    // Get the latest trade data for ETH/BTC
    Trades trades = marketDataService.getTrades(CurrencyPair.ETH_BTC);
    System.out.println("Trades, default. Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.ETH_BTC, 1L);
    System.out.println("Trades, last 1 hour= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.ETH_BTC, 5L);
    System.out.println("Trades, last 5 hour= " + trades.getTrades().size());

    System.out.println(trades.toString());
  }

  private static void raw(CryptopiaMarketDataServiceRaw marketDataService) throws IOException {
    // Get the latest trade data for ETH/BTC
    List<CryptopiaMarketHistory> trades =
        marketDataService.getCryptopiaTrades(CurrencyPair.ETH_BTC);
    System.out.println("Trades, default. Size= " + trades.size());

    trades = marketDataService.getCryptopiaTrades(CurrencyPair.ETH_BTC, 1L);
    System.out.println("Trades, last 1 hour= " + trades.size());

    trades = marketDataService.getCryptopiaTrades(CurrencyPair.ETH_BTC, 5L);
    System.out.println("Trades, last 5 hour= " + trades.size());

    System.out.println(trades.toString());
  }
}
