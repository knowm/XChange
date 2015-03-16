package com.xeiam.xchange.examples.bitkonan.marketdata;

import java.io.IOException;

import com.xeiam.xchange.bitkonan.BitKonanExchange;
import com.xeiam.xchange.bitkonan.service.polling.BitKonanMarketDataService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * <p>
 * BitKonanMarketdataDemo - simple example of OrderBook and Ticker API usage.
 * </p>
 *
 * @author Piotr Ładyżyński
 */
public class BitKonanMarketdataDemo {

  private static BitKonanExchange bitKonanExchange;
  private static BitKonanMarketDataService bitKonanMarketDataService;

  public static void main(String[] args) throws Exception {

    setUpExchange();
    requestAndPrintOrderBook(bitKonanMarketDataService);
    requestAndPrintLatestTicker(bitKonanMarketDataService);
  }

  public static void setUpExchange() {

    bitKonanExchange = new BitKonanExchange();
    bitKonanExchange.applySpecification(bitKonanExchange.getDefaultExchangeSpecification());
    bitKonanMarketDataService = (BitKonanMarketDataService) bitKonanExchange.getPollingMarketDataService();
  }

  public static void requestAndPrintOrderBook(PollingMarketDataService marketDataService) throws IOException {

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
  }

  public static void requestAndPrintLatestTicker(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker.toString());
  }

}