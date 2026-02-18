package org.knowm.xchange.dase.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Live adapter-level integration tests for public market data. Picked up by Failsafe using
 * *Integration.java Run with: mvn clean verify -DskipIntegrationTests=false
 */
public class DaseMarketDataServiceIntegration {

  private static final CurrencyPair PAIR = new CurrencyPair("BTC", "CZK");

  @Test
  public void ticker_via_adapter_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    MarketDataService svc = ex.getMarketDataService();

    Ticker t = svc.getTicker(PAIR);
    assertNotNull(t);
    assertNotNull(t.getLast());
    assertNotNull(t.getBid());
    assertNotNull(t.getAsk());
  }

  @Test
  public void orderbook_via_adapter_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    MarketDataService svc = ex.getMarketDataService();

    OrderBook ob = svc.getOrderBook(PAIR);
    assertNotNull(ob);
    assertFalse(ob.getAsks().isEmpty() && ob.getBids().isEmpty());
  }

  @Test
  public void trades_via_adapter_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    MarketDataService svc = ex.getMarketDataService();

    Trades tr = svc.getTrades(PAIR);
    assertNotNull(tr);
  }
}
