package com.xeiam.xchange.examples.btctrade.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btctrade.BTCTradeExchange;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting order book at BTCTrade.
 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTCTrade exchange API using default settings.
    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication).
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the order book data for BTC/CNY.
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(orderBook);
    System.out.println("asks: " + orderBook.getAsks());
    System.out.println("bids: " + orderBook.getBids());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication).
    BTCTradeMarketDataServiceRaw marketDataService = (BTCTradeMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the order book data for BTC/CNY.
    BTCTradeDepth depth = marketDataService.getBTCTradeDepth();
    System.out.println("size: " + (depth.getAsks().length + depth.getBids().length));
  }

}
