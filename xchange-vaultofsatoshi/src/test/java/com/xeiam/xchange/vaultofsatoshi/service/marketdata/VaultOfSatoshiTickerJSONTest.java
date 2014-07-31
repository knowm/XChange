package com.xeiam.xchange.vaultofsatoshi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.TickerWrapper;

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
    TickerWrapper VaultOfSatoshiTicker = mapper.readValue(is, TickerWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(VaultOfSatoshiTicker.getTicker().getLast()).isEqualTo(new BigDecimal("684.00000000"));
    assertThat(VaultOfSatoshiTicker.getTicker().getHigh()).isEqualTo(new BigDecimal("686.50000000"));
    assertThat(VaultOfSatoshiTicker.getTicker().getLow()).isEqualTo(new BigDecimal("601.00000000"));
    assertThat(VaultOfSatoshiTicker.getTicker().getVolume()).isEqualTo(new BigDecimal("29.32450256"));
  }

}
