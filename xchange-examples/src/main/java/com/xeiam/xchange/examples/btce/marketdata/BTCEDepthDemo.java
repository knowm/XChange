package com.xeiam.xchange.examples.btce.marketdata;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v3.service.polling.BTCEMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Order Book at BTC-E
 */
public class BTCEDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange btce = ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName());
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the latest full order book data for LTC/USD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.LTC_USD);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    // Get the latest partial order book (2000 entries) data for BTC/USD
    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD, 2000);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    // Get the latest partial size order book (3 entries) data for BTC/USD
    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD, 3);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    BTCEMarketDataServiceRaw marketDataService = (BTCEMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the latest full order book data for LTC/USD
    Map<String, BTCEDepth> depth = marketDataService.getBTCEDepth("ltc_usd", 7).getDepthMap();
    for (Map.Entry<String, BTCEDepth> entry : depth.entrySet()) {
      System.out.println("Pair: " + entry.getKey() + ", Depth:" + entry.getValue());
    }
  }

}
