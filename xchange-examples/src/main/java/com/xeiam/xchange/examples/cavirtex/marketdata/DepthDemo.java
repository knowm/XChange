package com.xeiam.xchange.examples.cavirtex.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.virtex.v2.VirtExExchange;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExDepth;
import com.xeiam.xchange.virtex.v2.service.polling.VirtExMarketDataServiceRaw;

/**
 * Demonstrate requesting Order Book at VirtEx
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the VirtEx exchange API using default settings
    Exchange cavirtex = ExchangeFactory.INSTANCE.createExchange(VirtExExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = cavirtex.getPollingMarketDataService();

    generic(marketDataService);
    raw((VirtExMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CAD);

    System.out.println("Current Order Book size for BTC / CAD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(VirtExMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    VirtExDepth orderBook = marketDataService.getVirtExOrderBook(CurrencyPair.BTC_CAD);

    System.out.println("Current Order Book size for BTC / CAD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("Last Ask: " + orderBook.getAsks().get(0)[0].toString());

    System.out.println("Last Bid: " + orderBook.getBids().get(0)[0].toString());

    System.out.println(orderBook.toString());
  }

}
