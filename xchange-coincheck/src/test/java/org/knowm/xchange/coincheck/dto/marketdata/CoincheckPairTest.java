package org.knowm.xchange.coincheck.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.coincheck.CoincheckTestUtil;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

class CoincheckPairTest {
  @Test
  @SneakyThrows
  void parseFromString() {
    ObjectMapper objectMapper = CoincheckTestUtil.createObjectMapper();
    String string = "\"btc_jpy\"";
    CoincheckPair expected = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    CoincheckPair parsed = objectMapper.readValue(string, CoincheckPair.class);
    assertThat(parsed).isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void parseFromUppercaseString() {
    ObjectMapper objectMapper = CoincheckTestUtil.createObjectMapper();
    String string = "\"BTC_JPY\"";
    CoincheckPair expected = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    CoincheckPair parsed = objectMapper.readValue(string, CoincheckPair.class);
    assertThat(parsed).isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void testToString() {
    CoincheckPair pair = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    assertThat(pair.toString()).isEqualTo("btc_jpy");
  }

  @Test
  @SneakyThrows
  void toJson() {
    ObjectMapper objectMapper = CoincheckTestUtil.createObjectMapper();
    CoincheckPair pair = new CoincheckPair(new CurrencyPair(Currency.BTC, Currency.JPY));
    String json = objectMapper.writeValueAsString(pair);
    assertThat(json).isEqualTo("\"btc_jpy\"");
  }
}
