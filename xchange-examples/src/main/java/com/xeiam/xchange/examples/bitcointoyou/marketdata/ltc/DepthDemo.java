package com.xeiam.xchange.examples.bitcointoyou.marketdata.ltc;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouExchange;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouOrderBook;
import com.xeiam.xchange.bitcointoyou.service.polling.BitcoinToYouMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Depth at BitcoinToYou
 * 
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BitcoinToYou exchange API using default settings
    Exchange bitcoinToYou = ExchangeFactory.INSTANCE.createExchange(BitcoinToYouExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcoinToYou.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitcoinToYouMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/BRL
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currencies.LTC, Currencies.BRL));

    System.out.println("Current Order Book size for BTC / BRL: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(BitcoinToYouMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/BRL
    BitcoinToYouOrderBook orderBook = marketDataService.getBitcoinToYouOrderBook(new CurrencyPair(Currencies.LTC, Currencies.BRL));

    System.out.println("Current Order Book size for BTC / BRL: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

}
