package org.knowm.xchange.dase.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class CandlesJSONTest {

  @Test
  void unmarshal() throws Exception {
    InputStream is =
        CandlesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dase/dto/marketdata/example-candles.json");
    ObjectMapper mapper = new ObjectMapper();
    DaseCandlesResponse res = mapper.readValue(is, DaseCandlesResponse.class);

    assertThat(res.getCandles()).isNotEmpty();
    assertThat(res.getCandles().get(0)).hasSize(6);
  }
}
