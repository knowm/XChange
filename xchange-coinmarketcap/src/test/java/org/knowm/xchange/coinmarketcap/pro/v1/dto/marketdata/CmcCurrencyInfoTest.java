package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;

class CmcCurrencyInfoTest {

  @Test
  void deserializeCurrencyInfo() throws Exception {
    // given
    InputStream is =
        CmcCurrencyInfo.class.getResourceAsStream(
            "/org/knowm/xchange/coinmarketcap/pro/v1/dto/marketdata/example-currency-info.json");

    // when
    ObjectMapper mapper = new ObjectMapper();
    CmcCurrencyInfo currencyInfo = mapper.readValue(is, CmcCurrencyInfo.class);

    // then
    assertThat(currencyInfo.getSymbol()).isEqualTo(Currency.BTC.getSymbol());
    assertThat(currencyInfo.getId()).isEqualTo(1);
    assertThat(currencyInfo.getUrls().getWebsite().get(0)).isEqualTo("https://bitcoin.org/");
  }
}
