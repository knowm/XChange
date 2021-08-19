package org.knowm.xchange.bitmex.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitmexOrderBookFetchIntegration {

  public static MarketDataService marketDataService;
  public static BitmexExchange bitmexExchange;

  @Before
  public void setUp() {
    bitmexExchange = (BitmexExchange) ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class);
    marketDataService = bitmexExchange.getMarketDataService();
  }

  @Test
  public void getOrderBookTest() throws IOException {
    CurrencyPair pair = bitmexExchange.determineActiveContract("ETH", "USD", BitmexPrompt.MONTHLY);
    OrderBook orderBook = marketDataService.getOrderBook(pair);

    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getAsks()).isNotEmpty();
    assertThat(orderBook.getBids()).isNotEmpty();

    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isGreaterThan(BigDecimal.ZERO);
    assertThat(orderBook.getAsks().get(0).getCurrencyPair()).isEqualTo(pair);

    assertThat(orderBook.getBids().get(0).getLimitPrice()).isGreaterThan(BigDecimal.ZERO);
    assertThat(orderBook.getAsks().get(0).getCurrencyPair()).isEqualTo(pair);
  }
}
