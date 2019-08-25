package org.knowm.xchange.examples.dsx.marketdata;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXExchangeV3;
import org.knowm.xchange.dsx.dto.marketdata.DSXOrderbook;
import org.knowm.xchange.dsx.service.DSXMarketDataServiceV3;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Pavel Chertalev */
public class DSXDepthDemoV3 {

  public static void main(String[] args) throws IOException {

    Exchange dsx = ExchangeFactory.INSTANCE.createExchange(DSXExchangeV3.class);
    generic(dsx);
    raw(dsx);
  }

  private static void generic(Exchange exchange) throws IOException {

    MarketDataService marketDataService = exchange.getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.LTC_USD, "LIVE");
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD, "DEMO");
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
    System.out.println("size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(Exchange exchange) throws IOException {

    DSXMarketDataServiceV3 marketDataService =
        (DSXMarketDataServiceV3) exchange.getMarketDataService();

    Map<String, DSXOrderbook> depth =
        marketDataService.getDSXOrderbook("ltcusd", "LIVE").getOrderbookMap();
    for (Map.Entry<String, DSXOrderbook> entry : depth.entrySet()) {
      System.out.println("Pair: " + entry.getKey() + ", Depth:" + entry.getValue());
    }
  }
}
