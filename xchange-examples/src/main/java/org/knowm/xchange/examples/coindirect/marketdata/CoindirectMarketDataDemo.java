package org.knowm.xchange.examples.coindirect.marketdata;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectExchange;
import org.knowm.xchange.coindirect.service.CoindirectMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.examples.coindirect.CoindirectDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CoindirectMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CoindirectDemoUtils.createExchange();

    /* create a data service from the exchange */
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(exchange, marketDataService);
    raw((CoindirectExchange) exchange, (CoindirectMarketDataService) marketDataService);
  }

  public static void generic(Exchange exchange, MarketDataService marketDataService)
      throws IOException {
      OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);

      System.out.println("Got order book "+orderBook);

  }

  public static void raw(CoindirectExchange exchange, CoindirectMarketDataService marketDataService)
      throws IOException {

  }

  public static void rawAll(CoindirectExchange exchange, CoindirectMarketDataService marketDataService)
      throws IOException {

  }
}
