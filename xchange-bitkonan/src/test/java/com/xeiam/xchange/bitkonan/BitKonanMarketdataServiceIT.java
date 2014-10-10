package com.xeiam.xchange.bitkonan;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.xeiam.xchange.bitkonan.service.polling.BitKonanMarketDataService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;

/**
 * Integration test requesting for OrderBook and Ticker from BitKonan Exchange.
 *
 * @author Piotr Ładyżyński
 */
public class BitKonanMarketdataServiceIT {

  private BitKonanExchange bitKonanExchange = new BitKonanExchange();

  @Ignore
  @Test
  public void testOrderBookRetrieval() throws IOException {

    bitKonanExchange.applySpecification(bitKonanExchange.getDefaultExchangeSpecification());
    BitKonanMarketDataService bitKonanMarketDataService = (BitKonanMarketDataService) bitKonanExchange.getPollingMarketDataService();

    OrderBook orderBook = bitKonanMarketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println(orderBook.toString());

  }

  @Ignore
  @Test
  public void testTickerRetrieval() throws IOException {

    bitKonanExchange.applySpecification(bitKonanExchange.getDefaultExchangeSpecification());
    BitKonanMarketDataService bitKonanMarketDataService = (BitKonanMarketDataService) bitKonanExchange.getPollingMarketDataService();

    Ticker ticker = bitKonanMarketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());

  }

}
