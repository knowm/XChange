package org.knowm.xchange.hitbtc.v2.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

public class HitbtcCandleJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        HitbtcCandleJsonTest.class.getResourceAsStream(
            "/org/knowm/xchange/hitbtc/v2/dto/marketdata/example-candles-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    TypeReference<List<HitbtcCandle>> typeReference = new TypeReference<List<HitbtcCandle>>() {};

    List<HitbtcCandle> hitbtcCandles = mapper.readValue(is, typeReference);

    assertThat(hitbtcCandles).hasSize(720);
    HitbtcCandle candle = hitbtcCandles.get(0);
    assertThat(candle.getTimestamp()).isEqualTo("2017-02-28T08:00:00.000Z");
    assertThat(candle.getOpen()).isEqualTo("1125.27");
    assertThat(candle.getClose()).isEqualTo("1125.27");
    assertThat(candle.getMax()).isEqualTo("1125.27");
    assertThat(candle.getMin()).isEqualTo("1125.27");
    assertThat(candle.getVolume()).isEqualTo("0.01");
    assertThat(candle.getVolumeQuote()).isEqualTo("11.2527");
  }
}
