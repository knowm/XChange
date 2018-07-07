package org.knowm.xchange.hitbtc.v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.hitbtc.v2.BaseServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcCurrency;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HitbtcMarketDataServiceRawIntegration extends BaseServiceTest {

  private MarketDataService marketDataService = exchange().getMarketDataService();
  private HitbtcMarketDataServiceRaw marketDataServiceRaw =
      (HitbtcMarketDataServiceRaw) marketDataService;

  @Test
  public void testGetHitbtcSymbols() throws IOException {

    List<HitbtcSymbol> symbols = marketDataServiceRaw.getHitbtcSymbols();

    assertNotNull(symbols);
    assertFalse(symbols.isEmpty());
  }

  @Test
  public void testGetHitbtcCurrencies() throws IOException {

    List<HitbtcCurrency> currencies = marketDataServiceRaw.getHitbtcCurrencies();
    assertNotNull(currencies);
    assertFalse(currencies.isEmpty());

    HitbtcCurrency currency = marketDataServiceRaw.getHitbtcCurrency("btc");
    assertNotNull(currency);
    assertEquals("BTC", currency.getId());
  }

  @Test
  public void testGetHitbtcTickers() throws IOException {

    Map<String, HitbtcTicker> tickers = marketDataServiceRaw.getHitbtcTickers();

    assertThat(tickers).isNotEmpty();
    assertThat(tickers.get("BTCUSD")).isNotNull();
  }
}
