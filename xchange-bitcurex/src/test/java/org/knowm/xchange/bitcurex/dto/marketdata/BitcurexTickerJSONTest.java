package org.knowm.xchange.bitcurex.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BitcurexTicker JSON parsing
 */
public class BitcurexTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexTicker BitcurexTicker = mapper.readValue(is, BitcurexTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BitcurexTicker.getLast()).isEqualTo(new BigDecimal("1555"));
    assertThat(BitcurexTicker.getHigh()).isEqualTo(new BigDecimal("1555"));
    assertThat(BitcurexTicker.getLow()).isEqualTo(new BigDecimal("1538"));
    assertThat(BitcurexTicker.getVolume()).isEqualTo(new BigDecimal("42.67895867"));
  }

}
