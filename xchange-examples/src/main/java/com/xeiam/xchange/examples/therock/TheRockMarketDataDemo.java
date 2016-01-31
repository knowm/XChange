package com.xeiam.xchange.examples.therock;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.therock.TheRockExchange;

public class TheRockMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get TheRock exchange API using default settings
    Exchange theRockExchange = ExchangeFactory.INSTANCE.createExchange(TheRockExchange.class.getName());

    generic(theRockExchange);
  }

  private static void generic(Exchange loyalbitExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService loyalbitMarketDataService = loyalbitExchange.getPollingMarketDataService();

    // Get the ticker
    System.out.println("Ticker: " + loyalbitMarketDataService.getTicker(CurrencyPair.BTC_USD));

    // Get the order book
    System.out.println("Order book: " + loyalbitMarketDataService.getOrderBook(CurrencyPair.BTC_USD));
  }
}
