package org.knowm.xchange.latoken.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
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
  public void testGetTicker() throws IOException {
    Ticker ticker = marketService.getTicker(CurrencyPair.ETH_BTC);
    assertNotNull(ticker);
    System.out.println(ticker.toString());

    // Test bad request
    exceptionRule.expect(ExchangeException.class);
    exceptionRule.expectMessage("Pair BLABLA is not found (HTTP status code: 400)");
    marketService.getTicker(new CurrencyPair("BLA/BLA"));
  }

  @Test
  public void testGetTickers() throws IOException {
    List<Ticker> tickers = marketService.getTickers(null);
    assertNotNull(tickers);
    assertTrue(tickers.size() > 0);
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
