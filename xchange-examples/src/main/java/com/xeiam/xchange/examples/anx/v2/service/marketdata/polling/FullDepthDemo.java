package com.xeiam.xchange.examples.anx.v2.service.marketdata.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Test requesting full depth at MtGox
 */
public class FullDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the version 2 MtGox exchange API using default settings
    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = anx.getPollingMarketDataService();

    // Get the current full orderbook
    OrderBook fullOrderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    for (LimitOrder limitOrder : fullOrderBook.getAsks()) {
      System.out.println(limitOrder);
    }
    int i = 0;
    for (LimitOrder limitOrder : fullOrderBook.getBids()) {
      System.out.println(i + " : " + limitOrder);
      i++;
    }

    System.out.println("Current Full Order Book size for BTC / USD: " + (fullOrderBook.getAsks().size() + fullOrderBook.getBids().size()));

  }

}
