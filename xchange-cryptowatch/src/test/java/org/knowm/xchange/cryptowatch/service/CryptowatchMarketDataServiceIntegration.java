package org.knowm.xchange.cryptowatch.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptowatch.CryptowatchExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;

public class CryptowatchMarketDataServiceIntegration {
  private CryptowatchMarketDataService marketDataService;

  @Before
  public void setUp() {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(CryptowatchExchange.class.getName());
    marketDataService = (CryptowatchMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getOrderBook() throws IOException {
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR, "kraken");
    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getAsks()).isNotNull();
    assertThat(orderBook.getAsks()).isNotEmpty();
    assertThat(orderBook.getBids()).isNotNull();
    assertThat(orderBook.getBids()).isNotEmpty();
  }

  @Test
  public void getTrades() throws IOException {
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR, "kraken");
    assertThat(trades).isNotNull();
    assertThat(trades.getTrades()).isNotNull();
    assertThat(trades.getTrades()).isNotEmpty();
  }
}
