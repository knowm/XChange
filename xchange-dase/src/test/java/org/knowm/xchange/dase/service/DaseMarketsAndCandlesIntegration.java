package org.knowm.xchange.dase.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
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
public class DaseMarketsAndCandlesIntegration {

  private static final String DEFAULT_MARKET = "BTC-CZK";

  @Test
  public void markets_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    List<org.knowm.xchange.dase.dto.marketdata.DaseMarketConfig> markets = raw.getMarkets();
    assertNotNull(markets);
    if (!markets.isEmpty()) {
      DaseMarketConfig mc = markets.get(0);
      assertNotNull(mc.market);
      assertNotNull(mc.base);
      assertNotNull(mc.quote);
    }
  }

  @Test
  public void single_market_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    DaseMarketConfig mc = raw.getMarket(DEFAULT_MARKET);
    assertNotNull(mc);
    assertNotNull(mc.market);
    assertNotNull(mc.pricePrecision);
    assertNotNull(mc.sizePrecision);
  }

  @Test
  public void candles_with_params_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    String granularity = "1m";
    long now = System.currentTimeMillis();
    long durationMs = 60_000L;
    int candles = 50;
    long to = now;
    long from = to - candles * durationMs;

    DaseCandlesResponse candlesRes = raw.getCandles(DEFAULT_MARKET, granularity, from, to);
    assertNotNull(candlesRes);
    if (candlesRes.getCandles() != null && !candlesRes.getCandles().isEmpty()) {
      List<java.math.BigDecimal> first = candlesRes.getCandles().get(0);
      assertTrue(first.size() >= 6);
    }
  }

  @Test
  public void exchange_symbols_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    List<CurrencyPair> symbols = raw.getExchangeSymbols();
    assertNotNull(symbols);
    if (!symbols.isEmpty()) {
      CurrencyPair first = symbols.get(0);
      assertNotNull(first.getBase());
      assertNotNull(first.getCounter());
    }
  }
}
