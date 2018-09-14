package org.knowm.xchange.coinbasepro.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class CoinbaseProCandleTest {

  @Test
  public void unmarshalTest() throws IOException {

    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/coinbasepro/dto/marketdata/example-historical-candles.json");
    CoinbaseProCandle[] candles = mapper.readValue(is, CoinbaseProCandle[].class);

    assertThat(candles).hasSize(10);

    CoinbaseProCandle candle = candles[0];
    assertThat(candle.getTime().getTime()).isEqualTo(1517443740000L);
    assertThat(candle.getOpen()).isEqualTo("10106.01");
    assertThat(candle.getHigh()).isEqualTo("10120.21");
    assertThat(candle.getLow()).isEqualTo("10106");
    assertThat(candle.getClose()).isEqualTo("10120.21");
    assertThat(candle.getVolume()).isEqualTo("2.85107455");
  }
}
