package com.xeiam.xchange.examples.cexio.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.cexio.CexIOExchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Author: brox Since: 2/6/14
 */

public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the latest order book data for GHs/BTC
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currency.GHs, Currency.BTC));

    System.out.println("Current Order Book size for GHS/BTC: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Timestamp: " + orderBook.getTimeStamp().toString());
    // System.out.println(orderBook.toString());
  }

}
