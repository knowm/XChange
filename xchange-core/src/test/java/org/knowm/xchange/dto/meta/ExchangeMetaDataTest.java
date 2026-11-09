package org.knowm.xchange.dto.meta;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;

class ExchangeMetaDataTest {

  /** 1 call per second => 1000ms delay */
  @Test
  void getPollDelayMillis1000() {
    RateLimit limit = new RateLimit(1, 1, SECONDS);
    assertThat(limit.getPollDelayMillis()).isEqualTo(1000L);
  }

  /** 2 calls per second => 500ms delay */
  @Test
  void getPollDelayMillis500() {
    RateLimit limit = new RateLimit(2, 1, SECONDS);
    assertThat(limit.getPollDelayMillis()).isEqualTo(500L);
  }

  /** 1 cal per second or 2 calls per second => 1000ms delay (500ms for burst calls) */
  @Test
  void getPollDelayMillisMulti() {
    assertThat(
            (long)
                ExchangeMetaData.getPollDelayMillis(
                    new RateLimit[] {new RateLimit(2, 1, SECONDS), new RateLimit(1, 1, SECONDS)}))
        .isEqualTo(1000L);
  }

  /** null for an unknown value */
  @Test
  void getPollDelayMillisNull() {
    assertThat(ExchangeMetaData.getPollDelayMillis(null)).isNull();
  }

  /** null for an unknown value */
  @Test
  void getPollDelayMillisEmpty() {
    assertThat(ExchangeMetaData.getPollDelayMillis(new RateLimit[0])).isNull();
  }

  @Test
  void shouldDeserialize() throws Exception {
    InputStream is =
        ExchangeMetaDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/core/meta/exchange-metadata.json");

    ObjectMapper mapper = new ObjectMapper();
    ExchangeMetaData metaData = mapper.readValue(is, ExchangeMetaData.class);
    assertThat(metaData.getInstruments().get(CurrencyPair.BTC_USD).getTradingFeeCurrency())
        .isEqualTo(Currency.USD);
    assertThat(metaData.getInstruments().get(CurrencyPair.BTC_USD).getPriceScale()).isEqualTo(2);
    assertThat(metaData.getInstruments().get(CurrencyPair.BTC_USD).getMinimumAmount())
        .isEqualTo(new BigDecimal("0.0001"));
    assertThat(metaData.getInstruments().get(CurrencyPair.BTC_USD).getMaximumAmount())
        .isEqualByComparingTo(new BigDecimal("100"));

    assertThat(
            metaData.getInstruments().get(new FuturesContract("BTC/USD/USDT")).getMaximumAmount())
        .isEqualByComparingTo(BigDecimal.valueOf(1));
  }
}
