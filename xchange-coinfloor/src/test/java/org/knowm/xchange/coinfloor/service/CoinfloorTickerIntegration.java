package org.knowm.xchange.coinfloor.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinfloor.CoinfloorExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinfloorTickerIntegration {

  @Test
  public void fetchTickerTest() throws IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinfloorExchange.class.getName());
    MarketDataService service = exchange.getMarketDataService();

    Ticker ticker = service.getTicker(CurrencyPair.BTC_GBP);
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_GBP);
    assertThat(ticker.getLast()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getHigh()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getLow()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getVwap()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getVolume()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getBid()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getAsk()).isGreaterThan(BigDecimal.ZERO);
  }
}
