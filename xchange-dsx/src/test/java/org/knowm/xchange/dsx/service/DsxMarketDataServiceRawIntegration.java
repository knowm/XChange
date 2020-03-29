package org.knowm.xchange.dsx.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.dsx.BaseServiceTest;
import org.knowm.xchange.dsx.dto.DsxCurrency;
import org.knowm.xchange.dsx.dto.DsxSymbol;
import org.knowm.xchange.dsx.dto.DsxTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DsxMarketDataServiceRawIntegration extends BaseServiceTest {

  private MarketDataService marketDataService = exchange().getMarketDataService();
  private DsxMarketDataServiceRaw marketDataServiceRaw =
      (DsxMarketDataServiceRaw) marketDataService;

  @Test
  public void testGetDsxSymbols() throws IOException {

    List<DsxSymbol> symbols = marketDataServiceRaw.getDsxSymbols();

    assertNotNull(symbols);
    assertFalse(symbols.isEmpty());
  }

  @Test
  public void testGetDsxCurrencies() throws IOException {

    List<DsxCurrency> currencies = marketDataServiceRaw.getDsxCurrencies();
    assertNotNull(currencies);
    assertFalse(currencies.isEmpty());

    DsxCurrency currency = marketDataServiceRaw.getDsxCurrency("btc");
    assertNotNull(currency);
    assertEquals("BTC", currency.getId());
  }

  @Test
  public void testGetDsxTickers() throws IOException {

    Map<String, DsxTicker> tickers = marketDataServiceRaw.getDsxTickers();

    assertThat(tickers).isNotEmpty();
    assertThat(tickers.get("BTCUSD")).isNotNull();
  }
}
