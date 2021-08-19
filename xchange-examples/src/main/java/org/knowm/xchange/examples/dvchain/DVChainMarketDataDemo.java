package org.knowm.xchange.examples.dvchain;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dvchain.DVChainExchange;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketData;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketResponse;
import org.knowm.xchange.dvchain.service.DVChainMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DVChainMarketDataDemo {
  public static void main(String[] args) throws IOException {

    // Use the factory to get DVChain exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class);

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((DVChainMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest order book data for BTC/USD
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

  private static void raw(DVChainMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for BTC/CAD
    DVChainMarketResponse orderBook = marketDataService.getMarketData();

    DVChainMarketData btcMarketData = orderBook.getMarketData().get("BTC");

    System.out.println(
        "Current Order Book size for BTC / USD: " + (btcMarketData.getLevels().size()));

    System.out.println("First Ask: " + btcMarketData.getLevels().get(0).getBuyPrice().toString());

    System.out.println("First Bid: " + btcMarketData.getLevels().get(0).getSellPrice().toString());

    System.out.println(orderBook.toString());
  }
}
