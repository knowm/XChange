package org.knowm.xchange.examples.btctrade.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btctrade.BTCTradeExchange;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import org.knowm.xchange.btctrade.service.BTCTradeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting order book at BTCTrade. */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTCTrade exchange API using default settings.
    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication).
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the order book data for BTC/CNY.
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(orderBook);
    System.out.println("asks: " + orderBook.getAsks());
    System.out.println("bids: " + orderBook.getBids());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication).
    BTCTradeMarketDataServiceRaw marketDataService =
        (BTCTradeMarketDataServiceRaw) exchange.getMarketDataService();

    // Get the order book data for BTC/CNY.
    BTCTradeDepth depth = marketDataService.getBTCTradeDepth();
    System.out.println("size: " + (depth.getAsks().length + depth.getBids().length));
  }
}
