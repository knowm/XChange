package org.knowm.xchange.hitbtc.v2.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.hitbtc.v2.BaseServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcCurrency;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

class HitbtcMarketDataServiceRawIntegration extends BaseServiceTest {

  private MarketDataService marketDataService = exchange().getMarketDataService();
  private HitbtcMarketDataServiceRaw marketDataServiceRaw =
      (HitbtcMarketDataServiceRaw) marketDataService;

  @Test
  void getHitbtcSymbols() throws Exception {

    List<HitbtcSymbol> symbols = marketDataServiceRaw.getHitbtcSymbols();

    assertThat(symbols).isNotNull();
    assertThat(symbols.isEmpty()).isFalse();
  }

  @Test
  void getHitbtcCurrencies() throws Exception {

    List<HitbtcCurrency> currencies = marketDataServiceRaw.getHitbtcCurrencies();
    assertThat(currencies).isNotNull();
    assertThat(currencies.isEmpty()).isFalse();

    HitbtcCurrency currency = marketDataServiceRaw.getHitbtcCurrency("btc");
    assertThat(currency).isNotNull();
    assertThat(currency.getId()).isEqualTo("BTC");
  }

  @Test
  void getHitbtcTickers() throws Exception {

    Map<String, HitbtcTicker> tickers = marketDataServiceRaw.getHitbtcTickers();

    assertThat(tickers).isNotEmpty();
    assertThat(tickers.get("BTCUSD")).isNotNull();
  }
}
