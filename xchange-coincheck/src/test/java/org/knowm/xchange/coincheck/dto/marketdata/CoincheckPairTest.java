package org.knowm.xchange.coincheck.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.knowm.xchange.coincheck.CoincheckTestUtil;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class CoincheckPairTest {
  @Test
  @SneakyThrows
  public void testParseFromString() {
    ObjectMapper objectMapper = CoincheckTestUtil.createObjectMapper();
    String string = "\"btc_jpy\"";
    CoincheckPair expected = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    CoincheckPair parsed = objectMapper.readValue(string, CoincheckPair.class);
    assertThat(parsed).isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  public void testParseFromUppercaseString() {
    ObjectMapper objectMapper = CoincheckTestUtil.createObjectMapper();
    String string = "\"BTC_JPY\"";
    CoincheckPair expected = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    CoincheckPair parsed = objectMapper.readValue(string, CoincheckPair.class);
    assertThat(parsed).isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  public void testToString() {
    CoincheckPair pair = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    assertThat(pair.toString()).isEqualTo("btc_jpy");
  }

  @Test
  @SneakyThrows
  public void testToJson() {
    ObjectMapper objectMapper = CoincheckTestUtil.createObjectMapper();
    CoincheckPair pair = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    String json = objectMapper.writeValueAsString(pair);
    assertThat(json).isEqualTo("\"btc_jpy\"");
  }
}
