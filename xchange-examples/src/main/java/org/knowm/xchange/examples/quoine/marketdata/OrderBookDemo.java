package org.knowm.xchange.examples.quoine.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.quoine.QuoineExchange;
import org.knowm.xchange.quoine.dto.marketdata.QuoineOrderBook;
import org.knowm.xchange.quoine.service.polling.QuoineMarketDataServiceRaw;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class OrderBookDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Quoine exchange API using default settings
    Exchange quoineExchange = ExchangeFactory.INSTANCE.createExchange(QuoineExchange.class.getName());

    generic(quoineExchange);
    raw(quoineExchange);
  }

  private static void generic(Exchange quoineExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService quoineMarketDataService = quoineExchange.getPollingMarketDataService();

    // Get the latest full order book data for NMC/XRP
    OrderBook orderBook = quoineMarketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(Exchange quoineExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    QuoineMarketDataServiceRaw quoineMarketDataService = (QuoineMarketDataServiceRaw) quoineExchange.getPollingMarketDataService();

    // Get the latest order book data
    QuoineOrderBook quoineOrderBook = quoineMarketDataService.getOrderBook(1);
    System.out.println(quoineOrderBook.toString());
    System.out.println("size: " + (quoineOrderBook.getSellPriceLevels().size() + quoineOrderBook.getBuyPriceLevels().size()));
  }
}
