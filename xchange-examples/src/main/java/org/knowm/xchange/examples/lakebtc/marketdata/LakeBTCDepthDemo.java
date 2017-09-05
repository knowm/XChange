package org.knowm.xchange.examples.lakebtc.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import org.knowm.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import org.knowm.xchange.lakebtc.service.LakeBTCMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

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

    MarketDataService marketDataService = lakebtcExchange.getMarketDataService();

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
    LakeBTCMarketDataServiceRaw marketDataService = (LakeBTCMarketDataServiceRaw) lakeBtcExchange.getMarketDataService();

    // Get the latest full order book data
    LakeBTCOrderBook orderBook = marketDataService.getLakeOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().length + orderBook.getBids().length));

  }
}
