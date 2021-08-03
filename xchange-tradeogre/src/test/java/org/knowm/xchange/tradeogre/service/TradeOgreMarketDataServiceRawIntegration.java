package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.tradeogre.TradeOgreExchange;

public class TradeOgreMarketDataServiceRawIntegration {

  private static Exchange tradeOgreExchange;

  @BeforeClass
  public static void setUpBaseClass() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(TradeOgreExchange.class);
    tradeOgreExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }

  @Test
  public void testGetTicker() throws IOException {
    CurrencyPair market = CurrencyPair.ETH_BTC;
    Ticker ticker = tradeOgreExchange.getMarketDataService().getTicker(market);
    Assert.assertEquals(market, ticker.getCurrencyPair());
    Assert.assertNotNull(ticker.getLast());
  }

  @Test
  public void testGetTickers() throws IOException {
    List<Ticker> tickers = tradeOgreExchange.getMarketDataService().getTickers(null);
    Assert.assertNotNull(tickers);
    Assert.assertFalse(tickers.isEmpty());
  }

  @Test
  public void testGetOrderBook() throws IOException {
    OrderBook orderBook =
        tradeOgreExchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_BTC);
    Assert.assertNotNull(orderBook);
    Assert.assertFalse(orderBook.getAsks().isEmpty());
    Assert.assertFalse(orderBook.getBids().isEmpty());
    orderBook
        .getAsks()
        .forEach(
            ask ->
                orderBook
                    .getBids()
                    .forEach(
                        bid ->
                            Assert.assertTrue(
                                bid.getLimitPrice().compareTo(ask.getLimitPrice()) < 0)));
  }
}
