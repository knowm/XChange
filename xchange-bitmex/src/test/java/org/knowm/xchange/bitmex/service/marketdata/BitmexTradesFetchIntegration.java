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
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitmexTradesFetchIntegration {

  private static MarketDataService marketDataService;
  private static BitmexExchange bitmexExchange;

  @Before
  public void setUp() {
    bitmexExchange = (BitmexExchange) ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class);
    marketDataService = bitmexExchange.getMarketDataService();
  }

  @Test
  public void getTradesTest() throws IOException {
    CurrencyPair pair = bitmexExchange.determineActiveContract("BTC", "USD", BitmexPrompt.MONTHLY);
    Trades trades = marketDataService.getTrades(pair, 500, 0L);

    assertThat(trades).isNotNull();
    assertThat(trades.getTrades()).isNotEmpty();
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(pair);
    assertThat(trades.getTrades().get(0).getType()).isNotNull();
    assertThat(trades.getTrades().get(0).getOriginalAmount()).isGreaterThan(BigDecimal.ZERO);
    assertThat(trades.getTrades().get(0).getPrice()).isGreaterThan(BigDecimal.ZERO);
    assertThat(trades.getTrades().get(0).getTimestamp()).isNotNull();
    assertThat(trades.getTrades().get(0).getId()).isNotNull();
  }
}
