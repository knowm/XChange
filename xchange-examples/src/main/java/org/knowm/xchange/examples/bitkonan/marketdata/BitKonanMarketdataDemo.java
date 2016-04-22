package org.knowm.xchange.examples.bitkonan.marketdata;

import java.io.IOException;

import org.knowm.xchange.bitkonan.BitKonanExchange;
import org.knowm.xchange.bitkonan.service.polling.BitKonanMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;
import org.knowm.xchange.utils.CertHelper;

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

    CertHelper.trustAllCerts();
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
    for (CurrencyPair pair : bitKonanExchange.getMetaData().getMarketMetaDataMap().keySet()) {
      OrderBook orderBook = marketDataService.getOrderBook(pair);
      System.out.println(orderBook.toString());

    }
  }

  public static void requestAndPrintLatestTicker(PollingMarketDataService marketDataService) throws IOException {
    for (CurrencyPair pair : bitKonanExchange.getMetaData().getMarketMetaDataMap().keySet()) {
      Ticker ticker = marketDataService.getTicker(pair);
      System.out.println(ticker.toString());
    }
  }

}