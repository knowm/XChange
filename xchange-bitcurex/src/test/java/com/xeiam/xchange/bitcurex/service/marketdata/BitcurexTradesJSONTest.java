package com.xeiam.xchange.bitcurex.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;

/**
 * Test BitcurexTrade[] JSON parsing
 */
public class BitcurexTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexTrade[] BitcurexTrades = mapper.readValue(is, BitcurexTrade[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BitcurexTrades[0].getPrice()).isEqualTo(new BigDecimal("70.00000000"));
  }
}
