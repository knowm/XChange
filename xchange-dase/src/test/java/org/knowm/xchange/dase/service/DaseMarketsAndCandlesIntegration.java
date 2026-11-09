package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.marketdata.DaseCandlesResponse;
import org.knowm.xchange.dase.dto.marketdata.DaseMarketConfig;

/**
 * Live integration tests for markets, single market, candles (with timeframe/from/to), and exchange
 * symbols. Picked up by Failsafe using *Integration.java when run with: mvn clean verify
 * -DskipIntegrationTests=false
 */
class DaseMarketsAndCandlesIntegration {

  private static final String DEFAULT_MARKET = "BTC-CZK";

  @Test
  void markets_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    List<org.knowm.xchange.dase.dto.marketdata.DaseMarketConfig> markets = raw.getMarkets();
    assertThat(markets).isNotNull();
    if (!markets.isEmpty()) {
      DaseMarketConfig mc = markets.get(0);
      assertThat(mc.market).isNotNull();
      assertThat(mc.base).isNotNull();
      assertThat(mc.quote).isNotNull();
    }
  }

  @Test
  void single_market_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    DaseMarketConfig mc = raw.getMarket(DEFAULT_MARKET);
    assertThat(mc).isNotNull();
    assertThat(mc.market).isNotNull();
    assertThat(mc.pricePrecision).isNotNull();
    assertThat(mc.sizePrecision).isNotNull();
  }

  @Test
  void candles_with_params_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    String granularity = "1m";
    long now = System.currentTimeMillis();
    long durationMs = 60_000L;
    int candles = 50;
    long to = now;
    long from = to - candles * durationMs;

    DaseCandlesResponse candlesRes = raw.getCandles(DEFAULT_MARKET, granularity, from, to);
    assertThat(candlesRes).isNotNull();
    if (candlesRes.getCandles() != null && !candlesRes.getCandles().isEmpty()) {
      List<java.math.BigDecimal> first = candlesRes.getCandles().get(0);
      assertThat(first.size() >= 6).isTrue();
    }
  }

  @Test
  void exchange_symbols_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    List<CurrencyPair> symbols = raw.getExchangeSymbols();
    assertThat(symbols).isNotNull();
    if (!symbols.isEmpty()) {
      CurrencyPair first = symbols.get(0);
      assertThat(first.getBase()).isNotNull();
      assertThat(first.getCounter()).isNotNull();
    }
  }
}
