package org.knowm.xchange.hitbtc.v2.service;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.knowm.xchange.hitbtc.v2.BaseServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import static org.assertj.core.api.Assertions.assertThat;

public class HitbtcMarketDataServiceRawIntegration extends BaseServiceTest {

  private MarketDataService marketDataService = exchange().getMarketDataService();
  private HitbtcMarketDataServiceRaw marketDataServiceRaw = (HitbtcMarketDataServiceRaw) marketDataService;

  @Test
  public void testGetHitbtcSymbols() throws IOException {

    List<HitbtcSymbol> symbols = marketDataServiceRaw.getHitbtcSymbols();

    assertNotNull(symbols);
    assertFalse(symbols.isEmpty());
  }

  @Test
  public void testGetHitbtcTickers() throws IOException {

    List<HitbtcTicker> tickers = marketDataServiceRaw.getHitbtcTickers();

    assertThat(tickers).isNotEmpty();
    Map<String, HitbtcTicker> tickerMap = tickers.stream().collect(
        Collectors.toMap(hitbtcTicker -> hitbtcTicker.getSymbol() , hitbtcTicker -> hitbtcTicker)
    );
    assertThat(tickerMap.get("BTCUSD")).isNotNull();
  }

}
