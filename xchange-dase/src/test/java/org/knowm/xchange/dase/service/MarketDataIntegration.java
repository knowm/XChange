package org.knowm.xchange.dase.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.junit.Test;
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
public class MarketDataIntegration {

  private static final String MARKET = "BTC-CZK";

  @Test
  public void ticker_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();
    DaseTicker t = raw.getTicker(MARKET);
    assertNotNull(t);
    assertNotNull(t.getPrice());
  }

  @Test
  public void orderbook_snapshot_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();
    DaseOrderBookSnapshot s = raw.getSnapshot(MARKET);
    assertNotNull(s);
    assertNotNull(s.getBids());
    assertNotNull(s.getAsks());
    assertFalse(s.getBids().isEmpty() && s.getAsks().isEmpty());
  }

  @Test
  public void trades_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();
    List<DaseTrade> trades = raw.getTrades(MARKET, null, null);
    assertNotNull(trades);
  }
}
