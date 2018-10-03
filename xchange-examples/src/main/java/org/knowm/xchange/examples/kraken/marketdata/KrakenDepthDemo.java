package org.knowm.xchange.examples.kraken.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KrakenDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange =
        ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService krakenMarketDataService = krakenExchange.getMarketDataService();

    // Get the latest full order book data for NMC/XRP
    OrderBook orderBook = krakenMarketDataService.getOrderBook(CurrencyPair.BTC_EUR);
    System.out.println(orderBook.toString());
    System.out.println(
        "full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    // Get the latest partial size order book data for NMC/XRP
    orderBook = krakenMarketDataService.getOrderBook(CurrencyPair.BTC_EUR, 3L);
    System.out.println(orderBook.toString());
    System.out.println(
        "partial orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService =
        (KrakenMarketDataServiceRaw) krakenExchange.getMarketDataService();

    // Get the latest full order book data
    KrakenDepth depth =
        krakenMarketDataService.getKrakenDepth(CurrencyPair.BTC_EUR, Long.MAX_VALUE);
    System.out.println(depth.toString());
    System.out.println("size: " + (depth.getAsks().size() + depth.getBids().size()));

    // Get the latest partial size order book data
    depth = krakenMarketDataService.getKrakenDepth(CurrencyPair.BTC_EUR, 3L);
    System.out.println(depth.toString());
    System.out.println("size: " + (depth.getAsks().size() + depth.getBids().size()));
  }
}
