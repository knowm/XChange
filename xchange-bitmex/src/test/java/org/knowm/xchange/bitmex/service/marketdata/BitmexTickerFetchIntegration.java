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
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitmexTickerFetchIntegration {

  public static MarketDataService marketDataService;
  public static BitmexExchange bitmexExchange;

  @Before
  public void setUp() {
    bitmexExchange = (BitmexExchange) ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class);
    marketDataService = bitmexExchange.getMarketDataService();
  }

  @Test(expected = ExchangeException.class)
  public void contractNotExistsTest() {
    bitmexExchange.determineActiveContract("USD", "USD", BitmexPrompt.PERPETUAL);
  }

  @Test
  public void determineActiveContractTest() {
    CurrencyPair perpetualXBT =
        bitmexExchange.determineActiveContract("BTC", "USD", BitmexPrompt.PERPETUAL);
    CurrencyPair perpetualETH =
        bitmexExchange.determineActiveContract("ETH", "BTC", BitmexPrompt.PERPETUAL);

    assertThat(new CurrencyPair("XBT", "USD")).isEqualTo(perpetualXBT);
    assertThat(new CurrencyPair("ETH", "USD")).isEqualTo(perpetualETH);
  }

  @Test
  public void fetchTickerTest() throws IOException {
    CurrencyPair activeContract =
        bitmexExchange.determineActiveContract("BTC", "USD", BitmexPrompt.PERPETUAL);

    Ticker ticker = marketDataService.getTicker(activeContract);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(activeContract);
    assertThat(ticker.getOpen()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getLast()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getBid()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getAsk()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getHigh()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getLow()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getVwap()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getVolume()).isGreaterThan(BigDecimal.ZERO);
  }
}
