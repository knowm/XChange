package com.xeiam.xchange.examples.loyalbit;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.loyalbit.LoyalbitExchange;
import com.xeiam.xchange.loyalbit.dto.marketdata.LoyalbitOrderBook;
import com.xeiam.xchange.loyalbit.service.polling.LoyalbitMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class LoyalbitMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Loyalbit exchange API using default settings
    Exchange loyalbitExchange = ExchangeFactory.INSTANCE.createExchange(LoyalbitExchange.class.getName());

    generic(loyalbitExchange);
    raw(loyalbitExchange);
  }

  private static void generic(Exchange loyalbitExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService loyalbitMarketDataService = loyalbitExchange.getPollingMarketDataService();

    // Get the ticker
    System.out.println("Ticker: " + loyalbitMarketDataService.getTicker(CurrencyPair.BTC_USD));

    // Get the latest full order book data for NMC/XRP
    OrderBook orderBook = loyalbitMarketDataService.getOrderBook(CurrencyPair.BTC_EUR);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    // Get the latest partial size order book data for NMC/XRP
    orderBook = loyalbitMarketDataService.getOrderBook(CurrencyPair.BTC_EUR, 3L);
    System.out.println(orderBook.toString());
    System.out.println("partial orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(Exchange loyalbitExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    LoyalbitMarketDataServiceRaw loyalbitMarketDataService = (LoyalbitMarketDataServiceRaw) loyalbitExchange.getPollingMarketDataService();

    // Get the ticker
    System.out.println("Ticker: " + loyalbitMarketDataService.getLoyalbitTicker());

    // Get the latest full order book data
    LoyalbitOrderBook depth = loyalbitMarketDataService.getLoyalbitOrderBook();
    System.out.println(depth.toString());
    System.out.println("size: " + (depth.getAsks().size() + depth.getBids().size()));
  }
}
