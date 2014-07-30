package com.xeiam.xchange.vaultofsatoshi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.TradesWrapper;

/**
 * Test VaultOfSatoshiTrades JSON parsing
 */
public class VaultOfSatoshiTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    TradesWrapper vosTrades = mapper.readValue(is, TradesWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(vosTrades.getTrades().get(0).getPrice().getValue()).isEqualTo(new BigDecimal("641.18165850"));
  }
}
