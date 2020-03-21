package org.knowm.xchange.examples.coinbasepro;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBook;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinbaseProDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Coinbase Pro exchange API using default settings
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((CoinbaseProMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD, 3);

    System.out.println(
        "Current Order Book size for BTC / USD: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println(
        "Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println(
        "Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(CoinbaseProMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    CoinbaseProProductBook orderBook =
        marketDataService.getCoinbaseProProductOrderBook(CurrencyPair.BTC_USD, 2);

    System.out.println(
        "Current Order Book size for BTC / USD: "
            + (orderBook.getAsks().length + orderBook.getBids().length));

    System.out.println("First Ask: " + orderBook.getAsks()[0].toString());

    System.out.println("First Bid: " + orderBook.getBids()[0].toString());

    System.out.println(orderBook.toString());
  }
}
