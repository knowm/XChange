package org.knowm.xchange.examples.btcmarkets;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.service.BTCMarketsMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BTCMarketsMarketDataDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get BTCMarkets exchange API using default settings
    Exchange btcMarketsExchange =
        ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getName());
    generic(btcMarketsExchange);
    raw(btcMarketsExchange);
  }

  private static void generic(Exchange btcMarketsExchange) throws IOException {
    // Interested in the public market data feed (no authentication)
    MarketDataService btcMarketsMarketDataService = btcMarketsExchange.getMarketDataService();

    // Get the (daily) ticker
    System.out.println("Ticker: " + btcMarketsMarketDataService.getTicker(CurrencyPair.BTC_AUD));

    OrderBook orderBook = btcMarketsMarketDataService.getOrderBook(CurrencyPair.BTC_AUD);
    System.out.println(orderBook.toString());
    System.out.println(
        "full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
    System.out.println("First 10 offers:");
    java.util.List<LimitOrder> asks = orderBook.getAsks();
    for (int i = 0; i < asks.size() && i < 10; i++) {
      System.out.println(asks.get(i));
    }
  }

  private static void raw(Exchange btcMarketsExchange) throws IOException {
    // Interested in the public market data feed (no authentication)
    BTCMarketsMarketDataServiceRaw btcMarketsMarketDataService =
        (BTCMarketsMarketDataServiceRaw) btcMarketsExchange.getMarketDataService();

    // Get the weekly ticker
    System.out.println(
        "Ticker: " + btcMarketsMarketDataService.getBTCMarketsTicker(CurrencyPair.BTC_AUD));

    // Get the latest full order book data
    BTCMarketsOrderBook depth =
        btcMarketsMarketDataService.getBTCMarketsOrderBook(CurrencyPair.BTC_AUD);
    System.out.println(depth.toString());
    System.out.println("offers: " + (depth.getAsks().size()));
  }
}
