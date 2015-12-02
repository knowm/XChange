package com.xeiam.xchange.examples.mercadobitcoin.marketdata.ltc;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.mercadobitcoin.MercadoBitcoinExchange;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinOrderBook;
import com.xeiam.xchange.mercadobitcoin.service.polling.MercadoBitcoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Depth at Mercado Bitcoin
 * 
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Mercado Bitcoin exchange API using default settings
    Exchange mercadoBitcoin = ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = mercadoBitcoin.getPollingMarketDataService();

    generic(marketDataService);
    raw((MercadoBitcoinMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest order book data for LTC/BRL
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair(Currency.LTC, Currency.BRL));

    System.out.println("Current Order Book size for LTC / BRL: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(MercadoBitcoinMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for LTC/BRL
    MercadoBitcoinOrderBook orderBook = marketDataService.getMercadoBitcoinOrderBook(new CurrencyPair(Currency.LTC, Currency.BRL));

    System.out.println("Current Order Book size for LTC / BRL: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

}
