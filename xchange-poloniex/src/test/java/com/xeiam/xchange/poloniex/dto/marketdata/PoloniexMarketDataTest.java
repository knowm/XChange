package com.xeiam.xchange.poloniex.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PoloniexMarketDataTest {

  @Test
  public void testUnmarshallAllTickers() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is = PoloniexMarketDataTest.class.getResourceAsStream("/marketdata/currency-info.json");

    final ObjectMapper mapper = new ObjectMapper();

    final JavaType currencyInfoType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, PoloniexCurrencyInfo.class);
    final Map<String, PoloniexCurrencyInfo> currencyInfo = mapper.readValue(is, currencyInfoType);

    assertThat(currencyInfo).hasSize(2);

    PoloniexCurrencyInfo abyCurrencyInfo = currencyInfo.get("ABY");
    assertThat(abyCurrencyInfo.getMaxDailyWithdrawal()).isEqualTo("10000000");
    assertThat(abyCurrencyInfo.getTxFee()).isEqualTo("0.01");
    assertThat(abyCurrencyInfo.getMinConf()).isEqualTo(8);
    assertThat(abyCurrencyInfo.isDisabled()).isFalse();
  }
}
