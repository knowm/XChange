package org.knowm.xchange.examples.independentreserve.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;
import org.knowm.xchange.independentreserve.service.IndependentReserveMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Author: Aleksey Baryshnikov Date: 2/9/16 Demonstrate requesting Ether Depth at Independent
 * Reserve
 */
public class DepthDemoEth {
  public static void main(String[] args) throws IOException {

    // Use the factory to get IndependentReserve exchange API using default settings
    Exchange independentReserve =
        ExchangeFactory.INSTANCE.createExchange(IndependentReserveExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = independentReserve.getMarketDataService();

    generic(marketDataService);
    raw((IndependentReserveMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest order book data for ETH/USD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_USD);

    System.out.println(
        "Current Order Book size for ETH / USD: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println(
        "Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println(
        "Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());
  }

  private static void raw(IndependentReserveMarketDataServiceRaw marketDataService)
      throws IOException {

    // Get the latest order book data for ETH/USD
    IndependentReserveOrderBook orderBook =
        marketDataService.getIndependentReserveOrderBook(
            Currency.ETH.getCurrencyCode(), Currency.USD.getCurrencyCode());

    System.out.println(
        "Current Order Book size for ETH / USD: "
            + (orderBook.getSellOrders().size() + orderBook.getBuyOrders().size()));

    System.out.println("First Ask: " + orderBook.getSellOrders().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBuyOrders().get(0).toString());

    System.out.println(orderBook.toString());
  }
}
