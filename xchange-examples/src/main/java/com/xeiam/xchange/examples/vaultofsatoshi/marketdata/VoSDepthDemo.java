package com.xeiam.xchange.examples.vaultofsatoshi.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiExchange;

/**
 * Demonstrate requesting Order Book at Vault of Satoshi
 *
 * @author timmolter
 */
public class VoSDepthDemo {

  static Exchange vos = ExchangeFactory.INSTANCE.createExchange(VaultOfSatoshiExchange.class.getName());

  // Interested in the public polling market data feed (no authentication)
  static PollingMarketDataService marketDataService = vos.getPollingMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  public static void generic() throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CAD);

    // System.out.println(orderBook.toString());
    System.out.println("Date: " + orderBook.getTimeStamp());

    System.out.println("lowestAsk: " + orderBook.getAsks().get(0));
    System.out.println("highestAsk: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1));
    System.out.println("asks Size: " + orderBook.getAsks().size());
    System.out.println("lowestBid: " + orderBook.getBids().get(orderBook.getBids().size() - 1));
    System.out.println("highestBid: " + orderBook.getBids().get(0));
    System.out.println("bids Size: " + orderBook.getBids().size());

  }

  public static void raw() throws IOException {

  }
}
