package org.knowm.xchange.examples.independentreserve.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;
import org.knowm.xchange.independentreserve.service.polling.IndependentReserveMarketDataServiceRaw;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Author: Kamil Zbikowski Date: 4/9/15 Demonstrate requesting Depth at Independent Reserve
 */
public class DepthDemo {
  public static void main(String[] args) throws IOException {

    // Use the factory to get IndependentReserve exchange API using default settings
    Exchange independentReserve = ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = independentReserve.getPollingMarketDataService();

    generic(marketDataService);
    raw((IndependentReserveMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair("xbt", "usd"));

    System.out.println("Current Order Book size for BTC / USD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(IndependentReserveMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    IndependentReserveOrderBook orderBook = marketDataService.getIndependentReserveOrderBook("XBT", "USD");

    System.out.println("Current Order Book size for BTC / USD: " + (orderBook.getSellOrders().size() + orderBook.getBuyOrders().size()));

    System.out.println("First Ask: " + orderBook.getSellOrders().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBuyOrders().get(0).toString());

    System.out.println(orderBook.toString());
  }

}
