package org.knowm.xchange.dase.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Test;

public class TickerJSONTest {

  @Test
  public void unmarshal() throws Exception {
    InputStream is =
        TickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dase/dto/marketdata/example-ticker.json");
    ObjectMapper mapper = new ObjectMapper();
    DaseTicker t = mapper.readValue(is, DaseTicker.class);

    assertThat(t.getAsk()).isEqualByComparingTo("18.55");
    assertThat(t.getBid()).isEqualByComparingTo("18.50");
    assertThat(t.getPrice()).isEqualByComparingTo("18.51");
    assertThat(t.getVolume()).isEqualByComparingTo("4609.42366696");
    assertThat(t.getTime()).isGreaterThan(0);
  }
}
