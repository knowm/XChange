package com.xeiam.xchange.gatecoin.testclient.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.gatecoin.GatecoinExchange;
import com.xeiam.xchange.gatecoin.service.polling.GatecoinMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinDepth;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Gatecoin. You can access both the raw data from Gatecoin or the XChange generic DTO data format.
 */
public class GatecoinDepthDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get gatecoin exchange API using default settings
    Exchange gatecoin = ExchangeFactory.INSTANCE.createExchange(GatecoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = gatecoin.getPollingMarketDataService();

    generic(marketDataService);
    raw((GatecoinMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

     OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
  }

  private static void raw(GatecoinMarketDataServiceRaw marketDataService) throws IOException {

    GatecoinDepth[] asks = marketDataService.getGatecoinOrderBook(CurrencyPair.BTC_USD.toString()).getAsks();

    System.out.println(asks.toString());
  }

}
