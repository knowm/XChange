package org.knowm.xchange.examples.cexio.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Author: brox Since: 2/6/14 */
public class DepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest order book data for GHs/BTC
    OrderBook orderBook =
        marketDataService.getOrderBook(new CurrencyPair(Currency.GHs, Currency.BTC));

    System.out.println(
        "Current Order Book size for GHS/BTC: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));
    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Timestamp: " + orderBook.getTimeStamp().toString());
    // System.out.println(orderBook.toString());
  }
}
