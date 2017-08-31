package org.knowm.xchange.cryptopia.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptopiaMarketDataServiceIntegration {

  private static final Logger log = LoggerFactory.getLogger(CryptopiaMarketDataServiceIntegration.class);

  private static MarketDataService marketDataService;

  @BeforeClass
  public static void setupExchange() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CryptopiaExchange.class.getName());

    marketDataService = exchange.getMarketDataService();
  }

  @Test
  public void testGetOrderBook() throws IOException {
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);

    log.info(orderBook.toString());

    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getBids().size()).isGreaterThan(1);
    assertThat(orderBook.getAsks().size()).isGreaterThan(1);
  }

  @Test
  public void testGetTrades() throws IOException {
    Trades trades = marketDataService.getTrades(CurrencyPair.ETH_BTC);

    log.info(trades.toString());

    assertThat(trades).isNotNull();
    assertThat(trades.getTrades().size()).isGreaterThan(1);
    assertThat(trades.getTradeSortType()).isEqualTo(Trades.TradeSortType.SortByTimestamp);
  }

  @Test
  public void testGetTicker() throws IOException {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.ETH_BTC);

    log.info(ticker.toString());

    assertThat(ticker).isNotNull();
  }

}
