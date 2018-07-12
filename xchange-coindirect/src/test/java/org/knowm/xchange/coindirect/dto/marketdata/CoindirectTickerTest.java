package org.knowm.xchange.coindirect.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class CoindirectTickerTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoindirectTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/coindirect/dto/marketdata/example-ticker.json");

    ObjectMapper mapper = new ObjectMapper();
    CoindirectTicker coindirectTicker = mapper.readValue(is, CoindirectTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coindirectTicker.data.get(0).close).isEqualTo(new BigDecimal("0.06979"));
    assertThat(coindirectTicker.data.get(0).open).isEqualTo(new BigDecimal("0.06974"));
    assertThat(coindirectTicker.data.get(0).high).isEqualTo(new BigDecimal("0.06986"));
    assertThat(coindirectTicker.data.get(0).low).isEqualTo(new BigDecimal("0.06925"));
    assertThat(coindirectTicker.data.get(0).volume).isEqualTo(new BigDecimal("1.0"));
  }
}
