package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
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
class DaseMarketDataServiceIntegration {

  private static final CurrencyPair PAIR = new CurrencyPair("BTC", "CZK");

  @Test
  void ticker_via_adapter_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    MarketDataService svc = ex.getMarketDataService();

    Ticker t = svc.getTicker(PAIR);
    assertThat(t).isNotNull();
    assertThat(t.getLast()).isNotNull();
    assertThat(t.getBid()).isNotNull();
    assertThat(t.getAsk()).isNotNull();
  }

  @Test
  void orderbook_via_adapter_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    MarketDataService svc = ex.getMarketDataService();

    OrderBook ob = svc.getOrderBook(PAIR);
    assertThat(ob).isNotNull();
    assertThat(ob.getAsks().isEmpty() && ob.getBids().isEmpty()).isFalse();
  }

  @Test
  void trades_via_adapter_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    MarketDataService svc = ex.getMarketDataService();

    Trades tr = svc.getTrades(PAIR);
    assertThat(tr).isNotNull();
  }
}
