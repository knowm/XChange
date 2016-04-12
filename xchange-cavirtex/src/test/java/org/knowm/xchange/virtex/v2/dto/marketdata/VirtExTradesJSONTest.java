package org.knowm.xchange.virtex.v2.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test VirtExTrade[] JSON parsing
 */
public class VirtExTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTradesWrapper virtExTradesWrapper = mapper.readValue(is, VirtExTradesWrapper.class);
    ArrayList<VirtExTrade> virtexTrades = virtExTradesWrapper.getTrades();

    // Verify that the example data was unmarshalled correctly
    assertThat(virtexTrades.get(0).getPrice()).isEqualTo(new BigDecimal("545.060000000"));
  }
}
