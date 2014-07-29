package com.xeiam.xchange.virtex.v1.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTicker;

/**
 * Test VirtExTicker JSON parsing
 */
public class VirtExTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTicker VirtExTicker = mapper.readValue(is, VirtExTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(VirtExTicker.getLast()).isEqualTo(new BigDecimal("12.32900"));
    assertThat(VirtExTicker.getHigh()).isEqualTo(new BigDecimal("12.37989"));
    assertThat(VirtExTicker.getLow()).isEqualTo(new BigDecimal("11.64001"));
    assertThat(VirtExTicker.getVolume()).isEqualTo(new BigDecimal("1866.56"));
  }

}
