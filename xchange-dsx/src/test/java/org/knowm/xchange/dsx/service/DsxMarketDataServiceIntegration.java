package org.knowm.xchange.dsx.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.BaseServiceTest;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dsx.dto.DsxTradesSortBy;
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

    Trades trades =
        marketDataService.getTrades(
            CurrencyPair.BTC_USD, null, null, DsxTradesSortBy.id, DsxSort.ASC);

    assertNotNull(trades);

    Map<String, Trades> tradesMap =
        ((DsxMarketDataService) marketDataService)
            .getAllTrades(DsxSort.DESC, DsxTradesSortBy.timestamp, null, null, 1, 0);

    assertNotNull(tradesMap);
  }

  @Test
  public void testGetOrderBook() throws IOException {

    MarketDataService marketDataService = exchange().getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    assertNotNull(orderBook);
  }
}
