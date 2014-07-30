package com.xeiam.xchange.cryptonit.v2.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;

/**
 * Test CryptonitDepth JSON parsing
 */
public class CryptonitDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptonitDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitDepth = mapper.readValue(is, CryptonitOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptonitDepth.getOrder(60751).getAskAmount()).isEqualTo(new BigDecimal("8.74000000"));
  }
}
