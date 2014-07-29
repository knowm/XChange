package com.xeiam.xchange.bitcoinium.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTickerHistory;

/**
 * Test BitcoiniumTickerHistory JSON parsing
 */
public class BitcoiniumTickerHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumTickerHistoryJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumTickerHistory BitcoiniumTrades = mapper.readValue(is, BitcoiniumTickerHistory.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BitcoiniumTrades.getPriceHistoryList().get(0)).isEqualTo(new BigDecimal("138.98"));
  }
}
