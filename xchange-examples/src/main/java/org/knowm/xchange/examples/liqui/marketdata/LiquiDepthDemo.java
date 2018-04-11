package org.knowm.xchange.examples.liqui.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.examples.liqui.LiquiExampleUtil;
import org.knowm.xchange.liqui.dto.marketdata.LiquiDepth;
import org.knowm.xchange.liqui.service.LiquiMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LiquiDepthDemo {

  public static final void main(final String[] args) throws IOException {
    final Exchange exchange = LiquiExampleUtil.createTestExchange();

    generic(exchange);
    raw(exchange);
  }

  private static void generic(final Exchange liquiExchange) throws IOException {

    final MarketDataService marketDataService = liquiExchange.getMarketDataService();

    final OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.LTC_BTC);

    System.out.println(orderBook.toString());
    System.out.println(
        "full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));
  }

  private static void raw(final Exchange liquiExchange) throws IOException {

    final LiquiMarketDataServiceRaw liquiMarketDataServiceRaw =
        (LiquiMarketDataServiceRaw) liquiExchange.getMarketDataService();

    final LiquiDepth depth = liquiMarketDataServiceRaw.getDepth(CurrencyPair.LTC_BTC, 5);

    System.out.println(depth.toString());
    System.out.println("size: " + (depth.getAsks().size() + depth.getBids().size()));
  }
}
