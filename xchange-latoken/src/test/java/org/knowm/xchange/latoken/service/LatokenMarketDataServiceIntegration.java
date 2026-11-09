package org.knowm.xchange.latoken.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.latoken.LatokenExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

class LatokenMarketDataServiceIntegration {

  static Exchange exchange;
  static MarketDataService marketService;

  @BeforeAll
  static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(LatokenExchange.class);
    marketService = exchange.getMarketDataService();
  }

  @BeforeEach
  void before() {
    // Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  void latokenMarketDataService() {
    assertThat(marketService).isNotNull();
  }

  @Test
  void getOrderBook() throws Exception {
    OrderBook orderbook = marketService.getOrderBook(CurrencyPair.ETH_BTC);
    assertThat(orderbook).isNotNull();
    System.out.println(orderbook.toString());
  }

  @Test
  void getTrades() throws Exception {
    Trades trades = marketService.getTrades(CurrencyPair.ETH_BTC);
    assertThat(trades).isNotNull();
    assertThat(trades.getTrades().size() > 0).isTrue();
    System.out.println(trades.getTrades().get(0).toString());
  }
}
