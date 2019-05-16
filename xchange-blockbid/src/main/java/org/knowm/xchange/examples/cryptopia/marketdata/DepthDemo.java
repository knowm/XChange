package org.knowm.xchange.examples.cryptopia.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.cryptopia.service.CryptopiaMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Depth at Cryptopia */
public class DepthDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get Cryptopia exchange API using default settings
    Exchange cryptopia = ExchangeFactory.INSTANCE.createExchange(CryptopiaExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = cryptopia.getMarketDataService();

    generic(marketDataService);
    raw((CryptopiaMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    // Get the latest order book data for ETH/BTC
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);

    System.out.println(
        "Current Order Book size for BTC / USD: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println(
        "Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println(
        "Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());
  }

  private static void raw(CryptopiaMarketDataServiceRaw marketDataService) throws IOException {
    // Get the latest order book data for ETH/BTC
    CryptopiaOrderBook orderBook = marketDataService.getCryptopiaOrderBook(CurrencyPair.ETH_BTC);

    System.out.println(
        "Current Order Book size for BTC / USD: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
  }
}
