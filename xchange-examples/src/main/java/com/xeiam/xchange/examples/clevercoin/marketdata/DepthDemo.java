package com.xeiam.xchange.examples.clevercoin.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.clevercoin.CleverCoinExchange;
import com.xeiam.xchange.clevercoin.dto.marketdata.CleverCoinOrderBook;
import com.xeiam.xchange.clevercoin.service.polling.CleverCoinMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Depth at Clevercoin
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Clevercoin exchange API using default settings
    Exchange clevercoin = ExchangeFactory.INSTANCE.createExchange(CleverCoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = clevercoin.getPollingMarketDataService();

    generic(marketDataService);
    raw((CleverCoinMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);

    System.out.println("Current Order Book size for BTC / USD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(CleverCoinMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/EUR
    CleverCoinOrderBook orderBook = marketDataService.getCleverCoinOrderBook();

    System.out.println("Current Order Book size for BTC / EUR: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

}
