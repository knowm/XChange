package org.knowm.xchange.examples.bitso.marketdata;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitso.BitsoExchange;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.service.BitsoMarketDataServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Depth at Bitso
 *
 * @author Piotr Ładyżyński
 */
public class BitsoMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Bitso exchange API using default settings
    Exchange bitso = ExchangeFactory.INSTANCE.createExchange(BitsoExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitso.getMarketDataService();

    generic(marketDataService);
    raw((BitsoMarketDataServiceRaw) marketDataService);

  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    CurrencyPair cp = new CurrencyPair(Currency.BTC, Currency.MXN);
    Ticker ticker = marketDataService.getTicker(cp);
    System.out.println("Ticker: " + ticker);

    // Get the latest order book data for BTCMXN
    OrderBook orderBook = marketDataService.getOrderBook(cp);

    System.out.println("Current Order Book size for BTC / MXN: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println("Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println("Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

    System.out.println(orderBook.toString());

    // Get trades within the last hour
    Object[] args = {"hour"};
    List<Trade> trades = marketDataService.getTrades(cp, args).getTrades();
    System.out.println("Number Trades within last hour: " + trades.size());
    for (Trade t : trades) {
      System.out.println("     " + t);
    }
  }

  private static void raw(BitsoMarketDataServiceRaw marketDataService) throws IOException {
    BitsoTicker ticker = marketDataService.getBitsoTicker();
    System.out.println("Ticker: " + ticker);

    // Get the latest order book data for BTCMXN
    BitsoOrderBook orderBook = marketDataService.getBitsoOrderBook();

    System.out.println("Current Order Book size for BTC / MXN: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());
  }

}
