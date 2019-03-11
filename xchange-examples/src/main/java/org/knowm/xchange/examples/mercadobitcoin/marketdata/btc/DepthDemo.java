package org.knowm.xchange.examples.mercadobitcoin.marketdata.btc;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinOrderBook;
import org.knowm.xchange.mercadobitcoin.service.MercadoBitcoinMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Depth at Mercado Bitcoin
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Mercado Bitcoin exchange API using default settings
    Exchange mercadoBitcoin =
        ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = mercadoBitcoin.getMarketDataService();

    generic(marketDataService);
    raw((MercadoBitcoinMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/BRL
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_BRL);

    System.out.println(
        "Current Order Book size for BTC / BRL: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println(
        "Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println(
        "Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(MercadoBitcoinMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/BRL
    MercadoBitcoinOrderBook orderBook =
        marketDataService.getMercadoBitcoinOrderBook(CurrencyPair.BTC_BRL);

    System.out.println(
        "Current Order Book size for BTC / BRL: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }
}
