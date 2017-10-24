package org.knowm.xchange.examples.bitstamp.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Depth at Bitstamp
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitstamp exchange API using default settings
    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitstamp.getMarketDataService();

    generic(marketDataService);
    raw((BitstampMarketDataServiceRaw) marketDataService);

  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println("Current Order Book size for BTC / USD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    //    System.out.println(orderBook.toString());
  }

  private static void raw(BitstampMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/USD
    BitstampOrderBook orderBook = marketDataService.getBitstampOrderBook(CurrencyPair.BTC_USD);

    System.out.println("Current Order Book size for BTC / USD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    //    System.out.println(orderBook.toString());
  }

}
