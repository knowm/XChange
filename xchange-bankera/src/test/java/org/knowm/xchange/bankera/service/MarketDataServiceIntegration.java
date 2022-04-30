package org.knowm.xchange.bankera.service;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bankera.ExchangeUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Ignore("Need api key")
public class MarketDataServiceIntegration {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static MarketDataService marketDataService;

  @BeforeClass
  public static void init() {

    Exchange exchange = ExchangeUtils.createExchangeFromProperties();
    marketDataService = exchange.getMarketDataService();
  }

  @Test
  public void getTickerTest() throws Exception {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.ETH_BTC);
    assertNotNull(ticker);
    logger.info("Response: {}", ticker);
  }

  @Test(expected = ExchangeException.class)
  public void getTickerInvalidMarketTest() throws Exception {
    marketDataService.getTicker(CurrencyPair.ADA_BNB);
  }

  @Test
  public void getOrderBookTest() throws Exception {

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BNK_USDT);
    assertNotNull(orderBook);
    logger.info("Response: {}", orderBook);
  }

  @Test
  public void getTradesTest() throws Exception {

    Trades trades = marketDataService.getTrades(CurrencyPair.ETH_BTC);
    assertNotNull(trades);
    logger.info("Response: {}", trades);
  }
}
