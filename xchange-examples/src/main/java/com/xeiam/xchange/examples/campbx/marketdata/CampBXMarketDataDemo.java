package com.xeiam.xchange.examples.campbx.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.campbx.CampBXExchange;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXOrderBook;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;
import com.xeiam.xchange.campbx.service.polling.CampBXMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Market Data from CampBX
 */
public class CampBXMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get CampBX exchange API using default settings
    Exchange campBXExchange = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());
    generic(campBXExchange);
    raw(campBXExchange);
  }

  private static void generic(Exchange campBXExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService campBXGenericMarketDataService = campBXExchange.getPollingMarketDataService();

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

    // Interested in the public polling market data feed (no authentication)
    CampBXMarketDataServiceRaw campBXspecificMarketDataService = (CampBXMarketDataServiceRaw) campBXExchange.getPollingMarketDataService();

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
