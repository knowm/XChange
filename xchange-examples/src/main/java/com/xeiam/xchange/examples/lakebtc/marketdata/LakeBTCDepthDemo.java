package com.xeiam.xchange.examples.lakebtc.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Created by Cristi on 12/22/2014.
 */
public class LakeBTCDepthDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    PollingMarketDataService marketDataService = lakebtcExchange.getPollingMarketDataService();

    // Get the latest full order book data for NMC/XRP
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    // Get the latest partial size order book data for NMC/XRP
    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCMarketDataServiceRaw marketDataService = (LakeBTCMarketDataServiceRaw) lakeBtcExchange.getPollingMarketDataService();

    // Get the latest full order book data
    LakeBTCOrderBook orderBook = marketDataService.getLakeBTCOrderBookCNY();
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().length + orderBook.getBids().length));

    // Get the latest full order book data
    orderBook = marketDataService.getLakeBTCOrderBookUSD();
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().length + orderBook.getBids().length));

  }
}
