package org.knowm.xchange.poloniex.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PoloniexMarketDataTest {

  @Test
  void unmarshallAllTickers() throws Exception {

    final InputStream is =
        PoloniexMarketDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/poloniex/dto/marketdata/currency-info.json");

    final ObjectMapper mapper = new ObjectMapper();

    final JavaType currencyInfoType =
        mapper
            .getTypeFactory()
            .constructMapType(HashMap.class, String.class, PoloniexCurrencyInfo.class);
    final Map<String, PoloniexCurrencyInfo> currencyInfo = mapper.readValue(is, currencyInfoType);

    assertThat(currencyInfo).hasSize(2);

    PoloniexCurrencyInfo abyCurrencyInfo = currencyInfo.get("ABY");
    assertThat(abyCurrencyInfo.getTxFee()).isEqualTo("0.01");
    assertThat(abyCurrencyInfo.getMinConf()).isEqualTo(8);
    assertThat(abyCurrencyInfo.isDisabled()).isFalse();
  }
}
