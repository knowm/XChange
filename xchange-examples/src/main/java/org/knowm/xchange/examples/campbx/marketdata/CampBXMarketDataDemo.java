package org.knowm.xchange.examples.campbx.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.campbx.CampBXExchange;
import org.knowm.xchange.campbx.dto.marketdata.CampBXOrderBook;
import org.knowm.xchange.campbx.dto.marketdata.CampBXTicker;
import org.knowm.xchange.campbx.service.CampBXMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Market Data from CampBX */
public class CampBXMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get CampBX exchange API using default settings
    Exchange campBXExchange =
        ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());
    generic(campBXExchange);
    raw(campBXExchange);
  }

  private static void generic(Exchange campBXExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService campBXGenericMarketDataService = campBXExchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = campBXGenericMarketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = campBXGenericMarketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println("Order book: " + orderBook);
  }

  private static void raw(Exchange campBXExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    CampBXMarketDataServiceRaw campBXspecificMarketDataService =
        (CampBXMarketDataServiceRaw) campBXExchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    CampBXTicker campBXTicker = campBXspecificMarketDataService.getCampBXTicker();

    System.out.println("Last: " + campBXTicker.getLast());
    System.out.println("Bid: " + campBXTicker.getBid());
    System.out.println("Ask: " + campBXTicker.getAsk());

    // Get the latest order book data for BTC/USD
    CampBXOrderBook campBXOrderBook = campBXspecificMarketDataService.getCampBXOrderBook();

    System.out.println("Order book: " + campBXOrderBook);
  }
}
