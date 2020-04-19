package org.knowm.xchange.dsx.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.BaseServiceTest;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DsxMarketDataServiceIntegration extends BaseServiceTest {

  @Test
  public void testGetTicker() throws IOException {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    assertNotNull(ticker);
    assertEquals(CurrencyPair.BTC_USD, ticker.getCurrencyPair());
  }

  @Test
  public void testGetTicker_BCH() throws IOException {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BCH_USD);
    assertNotNull(ticker);
    assertEquals(CurrencyPair.BCH_USD, ticker.getCurrencyPair());
  }

  @Test
  public void testGetTrades() throws IOException {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);

    assertNotNull(trades);
  }

  @Test
  public void testGetOrderBook() throws IOException {

    MarketDataService marketDataService = exchange().getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    assertNotNull(orderBook);
  }
}
