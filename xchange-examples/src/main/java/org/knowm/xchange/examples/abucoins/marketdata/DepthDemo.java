package org.knowm.xchange.examples.abucoins.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.abucoins.AbucoinsExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Author: bryant_harris */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(AbucoinsExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest order book data for GHs/BTC
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println(
        "Current Order Book size for BTC/USD: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));
    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Timestamp: " + orderBook.getTimeStamp().toString());
    // System.out.println(orderBook.toString());
  }
}
