package com.xeiam.xchange.examples.bitso.marketdata;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitso.BitsoExchange;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoOrderBook;
import com.xeiam.xchange.bitso.service.polling.BitsoMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

import java.io.IOException;

/**
 * Demonstrate requesting Depth at Bitso
 * @author Piotr Ładyżyński
 */
public class DepthDemo {



  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitso exchange API using default settings
    Exchange bitso = ExchangeFactory.INSTANCE.createExchange(BitsoExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitso.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitsoMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTCMXN
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currencies.BTC, Currencies.MXN));

    System.out.println("Current Order Book size for BTC / MXN: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(BitsoMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTCMXN
    BitsoOrderBook orderBook = marketDataService.getBitsoOrderBook();

    System.out.println("Current Order Book size for BTC / MXN: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

}
