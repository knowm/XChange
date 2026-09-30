package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.marketdata.DaseOrderBookSnapshot;
import org.knowm.xchange.dase.dto.marketdata.DaseTicker;
import org.knowm.xchange.dase.dto.marketdata.DaseTrade;

/**
 * Live integration tests for public endpoints per XChange Best Practices. Picked up by Failsafe
 * using *Integration.java when run with: mvn clean verify -DskipIntegrationTests=false
 */
class MarketDataIntegration {

  private static final String MARKET = "BTC-CZK";

  @Test
  void ticker_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();
    DaseTicker t = raw.getTicker(MARKET);
    assertThat(t).isNotNull();
    assertThat(t.getPrice()).isNotNull();
  }

  @Test
  void orderbook_snapshot_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();
    DaseOrderBookSnapshot s = raw.getSnapshot(MARKET);
    assertThat(s).isNotNull();
    assertThat(s.getBids()).isNotNull();
    assertThat(s.getAsks()).isNotNull();
    assertThat(s.getBids().isEmpty() && s.getAsks().isEmpty()).isFalse();
  }

  @Test
  void trades_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();
    List<DaseTrade> trades = raw.getTrades(MARKET, null, null);
    assertThat(trades).isNotNull();
  }
}
