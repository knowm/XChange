package org.knowm.xchange.bitflyer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitflyer.BitflyerExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitflyerTickerIntegration {

  @Test
  public void fetchTickerTest() throws IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitflyerExchange.class.getName());
    MarketDataService service = exchange.getMarketDataService();

    Ticker ticker = service.getTicker(CurrencyPair.BTC_JPY);
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_JPY);
    assertThat(ticker.getHigh()).isNull();
    assertThat(ticker.getLow()).isNull();
    assertThat(ticker.getLast()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getVolume()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getBid()).isGreaterThan(BigDecimal.ZERO);
    assertThat(ticker.getAsk()).isGreaterThan(BigDecimal.ZERO);
  }
}
