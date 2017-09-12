package org.knowm.xchange.hitbtc.v2.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.hitbtc.v2.BaseServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HitbtcMarketDataServiceRawIntegration extends BaseServiceTest {

  @Test
  public void testGetHitbtcSymbols() throws IOException {

    MarketDataService marketDataService = exchange().getMarketDataService();

    HitbtcMarketDataServiceRaw marketDataServiceRaw = (HitbtcMarketDataServiceRaw) marketDataService;

    List<HitbtcSymbol> symbols = marketDataServiceRaw.getHitbtcSymbols();

    assertNotNull(symbols);
    assertFalse(symbols.isEmpty());
  }

}