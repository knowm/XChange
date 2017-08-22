package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.hitbtc.v2.BaseServiceTest;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HitbtcMarketDataServiceTest extends BaseServiceTest {

  @Test
  public void testGetTicker() throws IOException {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    assertNotNull(ticker);
    assertEquals(CurrencyPair.BTC_USD, ticker.getCurrencyPair());
  }

}