package com.xeiam.xchange.vaultofsatoshi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.DepthWrapper;

/**
 * Test VaultOfSatoshiDepth JSON parsing
 */
public class VaultOfSatoshiDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DepthWrapper vaultOfSatoshiDepth = mapper.readValue(is, DepthWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(vaultOfSatoshiDepth.getDepth().getAsks().get(0).getAmount().getValue()).isEqualTo(new BigDecimal("0.22000000"));
  }
}
