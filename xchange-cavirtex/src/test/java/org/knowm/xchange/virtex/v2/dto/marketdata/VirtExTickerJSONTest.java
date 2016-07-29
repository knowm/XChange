package org.knowm.xchange.virtex.v2.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test VirtExTicker JSON parsing
 */
public class VirtExTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTickerWrapper virtExTickerWrapper = mapper.readValue(is, VirtExTickerWrapper.class);
    VirtExTicker virtExTicker = virtExTickerWrapper.getTicker();

    // Verify that the example data was unmarshalled correctly
    assertThat(virtExTicker.getLast()).isEqualTo(new BigDecimal("545.060000000"));
    assertThat(virtExTicker.getHigh()).isEqualTo(new BigDecimal("574.000000000"));
    assertThat(virtExTicker.getLow()).isEqualTo(new BigDecimal("538.000000000"));
    assertThat(virtExTicker.getVolume()).isEqualTo(new BigDecimal("284.231600000"));
    assertThat(virtExTicker.getSell()).isEqualTo(new BigDecimal("545.06000"));
    assertThat(virtExTicker.getBuy()).isEqualTo(new BigDecimal("545.03000"));

  }

}
