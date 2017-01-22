package org.knowm.xchange.examples.taurus.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.taurus.TaurusExchange;
import org.knowm.xchange.taurus.dto.marketdata.TaurusOrderBook;
import org.knowm.xchange.taurus.service.TaurusMarketDataServiceRaw;

/**
 * Demonstrate requesting Depth at Taurus
 */
public class TaurusDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Taurus exchange API using default settings
    Exchange taurus = ExchangeFactory.INSTANCE.createExchange(TaurusExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = taurus.getMarketDataService();

    generic(marketDataService);
    raw((TaurusMarketDataServiceRaw) marketDataService);

  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CAD);

    System.out.println("Current Order Book size for BTC / CAD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(TaurusMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    TaurusOrderBook orderBook = marketDataService.getTaurusOrderBook();

    System.out.println("Current Order Book size for BTC / CAD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

}
