package com.xeiam.xchange.bitcoinium.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;

/**
 * Test BitcoiniumTicker JSON parsing
 */
public class BitcoiniumTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumTicker BitcoiniumTicker = mapper.readValue(is, BitcoiniumTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BitcoiniumTicker.getLast()).isEqualTo(new BigDecimal("914.88696"));
    assertThat(BitcoiniumTicker.getHigh()).isEqualTo(new BigDecimal("932.38"));
    assertThat(BitcoiniumTicker.getLow()).isEqualTo(new BigDecimal("848.479"));
    assertThat(BitcoiniumTicker.getVolume()).isEqualTo(new BigDecimal("13425"));
    assertThat(BitcoiniumTicker.isAllTimeHigh()).isFalse();
  }

}
