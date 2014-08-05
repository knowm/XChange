package com.xeiam.xchange.vaultofsatoshi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTicker;

/**
 * Test VaultOfSatoshiTicker JSON parsing
 */
public class VaultOfSatoshiTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType type = mapper.getTypeFactory().constructParametricType(VosResponse.class, VosTicker.class);
    VosResponse<VosTicker> VaultOfSatoshiTicker = mapper.readValue(is, type);

    // Verify that the example data was unmarshalled correctly
    assertThat(VaultOfSatoshiTicker.getData().getLast()).isEqualTo(new BigDecimal("684.00000000"));
    assertThat(VaultOfSatoshiTicker.getData().getHigh()).isEqualTo(new BigDecimal("686.50000000"));
    assertThat(VaultOfSatoshiTicker.getData().getLow()).isEqualTo(new BigDecimal("601.00000000"));
    assertThat(VaultOfSatoshiTicker.getData().getVolume()).isEqualTo(new BigDecimal("29.32450256"));
  }

}
