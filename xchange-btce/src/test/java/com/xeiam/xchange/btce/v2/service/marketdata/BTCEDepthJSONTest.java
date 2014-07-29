package com.xeiam.xchange.btce.v2.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCEDepth;

/**
 * Test BTCEDepth JSON parsing
 */
@Deprecated
@Ignore
public class BTCEDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCEDepthJSONTest.class.getResourceAsStream("/v2/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCEDepth BTCEDepth = mapper.readValue(is, BTCEDepth.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BTCEDepth.getAsks().get(0)[0]).isEqualTo(new BigDecimal("13.07"));
  }
}
