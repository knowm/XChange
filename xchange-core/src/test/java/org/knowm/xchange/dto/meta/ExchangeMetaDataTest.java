package org.knowm.xchange.dto.meta;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class ExchangeMetaDataTest {

  /** 1 call per second => 1000ms delay */
  @Test
  public void testGetPollDelayMillis1000() {
    RateLimit limit = new RateLimit(1, 1, SECONDS);
    assertEquals(1000L, limit.getPollDelayMillis());
  }

  /** 2 calls per second => 500ms delay */
  @Test
  public void testGetPollDelayMillis500() {
    RateLimit limit = new RateLimit(2, 1, SECONDS);
    assertEquals(500L, limit.getPollDelayMillis());
  }

  /** 1 cal per second or 2 calls per second => 1000ms delay (500ms for burst calls) */
  @Test
  public void testGetPollDelayMillisMulti() {
    assertEquals(
        1000L,
        (long)
            ExchangeMetaData.getPollDelayMillis(
                new RateLimit[] {new RateLimit(2, 1, SECONDS), new RateLimit(1, 1, SECONDS)}));
  }

  /** null for an unknown value */
  @Test
  public void testGetPollDelayMillisNull() {
    assertEquals(null, ExchangeMetaData.getPollDelayMillis(null));
  }

  /** null for an unknown value */
  @Test
  public void testGetPollDelayMillisEmpty() {
    assertEquals(null, ExchangeMetaData.getPollDelayMillis(new RateLimit[0]));
  }

  @Test
  public void shouldDeserialize() throws IOException {
    InputStream is =
        ExchangeMetaDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/core/meta/exchange-metadata.json");

    ObjectMapper mapper = new ObjectMapper();
    ExchangeMetaData metaData = mapper.readValue(is, ExchangeMetaData.class);

    assertThat(metaData.getCurrencyPairs().get(CurrencyPair.BTC_USD).getTradingFeeCurrency())
        .isEqualTo(Currency.USD);
    assertThat(metaData.getCurrencyPairs().get(CurrencyPair.BTC_USD).getPriceScale()).isEqualTo(2);
    assertThat(metaData.getCurrencyPairs().get(CurrencyPair.BTC_USD).getMinimumAmount())
        .isEqualTo(new BigDecimal("0.0001"));
    assertThat(metaData.getCurrencyPairs().get(CurrencyPair.BTC_USD).getMaximumAmount())
        .isEqualTo(new BigDecimal("100"));
  }
}
