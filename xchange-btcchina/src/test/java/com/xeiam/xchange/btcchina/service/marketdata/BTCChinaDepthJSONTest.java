package com.xeiam.xchange.btcchina.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;

/**
 * Test BTCChinaDepth JSON parsing
 */
public class BTCChinaDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCChinaDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCChinaDepth btcChinaDepth = mapper.readValue(is, BTCChinaDepth.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(btcChinaDepth.getAsks().get(0)[0]).isEqualTo(new BigDecimal("1.0e+14"));
  }
}
