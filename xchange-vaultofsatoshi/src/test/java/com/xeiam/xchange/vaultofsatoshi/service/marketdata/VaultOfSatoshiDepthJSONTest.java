package com.xeiam.xchange.vaultofsatoshi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;

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
    JavaType type = mapper.getTypeFactory().constructParametricType(VosResponse.class, VosDepth.class);
    VosResponse<VosDepth> vaultOfSatoshiDepth = mapper.readValue(is, type);

    // Verify that the example data was unmarshalled correctly
    assertThat(vaultOfSatoshiDepth.getData().getAsks().get(0).getAmount().getValue()).isEqualTo(new BigDecimal("0.22000000"));
  }
}
