package org.knowm.xchange.bitstamp.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test BitstampTicker JSON parsing */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        TickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    BitstampTicker bitstampTicker = mapper.readValue(is, BitstampTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitstampTicker.getLast()).isEqualTo(new BigDecimal("134.89"));
    assertThat(bitstampTicker.getHigh()).isEqualTo(new BigDecimal("138.22"));
    assertThat(bitstampTicker.getLow()).isEqualTo(new BigDecimal("131.79"));
    assertThat(bitstampTicker.getVwap()).isEqualTo(new BigDecimal("135.31"));
    assertThat(bitstampTicker.getVolume()).isEqualTo(new BigDecimal("21982.44926674"));
    assertThat(bitstampTicker.getBid()).isEqualTo(new BigDecimal("134.89"));
    assertThat(bitstampTicker.getAsk()).isEqualTo(new BigDecimal("134.92"));
    assertThat(bitstampTicker.getTimestamp()).isEqualTo(1381787133L);
  }
}
