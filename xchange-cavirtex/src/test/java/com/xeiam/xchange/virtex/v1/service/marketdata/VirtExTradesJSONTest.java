package com.xeiam.xchange.virtex.v1.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExTrade;

/**
 * Test VirtExTrade[] JSON parsing
 */
public class VirtExTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTrade[] VirtExTrades = mapper.readValue(is, VirtExTrade[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(VirtExTrades[0].getPrice()).isEqualTo(new BigDecimal("11.500000000"));
  }
}
