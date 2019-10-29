package org.knowm.xchange.latoken.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.latoken.LatokenExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LatokenMarketDataServiceTest {

  static Exchange exchange;
  static MarketDataService marketService;

  @Rule public ExpectedException exceptionRule = ExpectedException.none();

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(LatokenExchange.class.getName());
    marketService = exchange.getMarketDataService();
  }

  @Before
  public void before() {
    // Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void testLatokenMarketDataService() {
    assertNotNull(marketService);
  }

  @Test
  public void testGetOrderBook() throws IOException {
    OrderBook orderbook = marketService.getOrderBook(CurrencyPair.ETH_BTC);
    assertNotNull(orderbook);
    System.out.println(orderbook.toString());
  }

  @Test
  public void testGetTrades() throws IOException {
    Trades trades = marketService.getTrades(CurrencyPair.ETH_BTC);
    assertNotNull(trades);
    assertTrue(trades.getTrades().size() > 0);
    System.out.println(trades.getTrades().get(0).toString());
  }
}
