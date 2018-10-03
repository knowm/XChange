package org.knowm.xchange.examples.wex.marketdata;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.wex.v3.WexExchange;
import org.knowm.xchange.wex.v3.dto.marketdata.WexDepth;
import org.knowm.xchange.wex.v3.service.WexMarketDataServiceRaw;

/** Demonstrate requesting Order Book at BTC-E */
public class WexDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange btce = ExchangeFactory.INSTANCE.createExchange(WexExchange.class.getName());
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

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

    // Interested in the public market data feed (no authentication)
    WexMarketDataServiceRaw marketDataService =
        (WexMarketDataServiceRaw) exchange.getMarketDataService();

    // Get the latest full order book data for LTC/USD
    Map<String, WexDepth> depth = marketDataService.getBTCEDepth("ltc_usd", 7).getDepthMap();
    for (Map.Entry<String, WexDepth> entry : depth.entrySet()) {
      System.out.println("Pair: " + entry.getKey() + ", Depth:" + entry.getValue());
    }
  }
}
