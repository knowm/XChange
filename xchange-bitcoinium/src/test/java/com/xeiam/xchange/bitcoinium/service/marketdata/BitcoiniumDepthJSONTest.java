package com.xeiam.xchange.bitcoinium.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;

/**
 * Test BitcoiniumDepth JSON parsing
 */
public class BitcoiniumDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoiniumDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumOrderbook bitcoiniumDepth = mapper.readValue(is, BitcoiniumOrderbook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcoiniumDepth.getAskPriceList().get(0)).isEqualTo(new BigDecimal("132.79"));
  }
}
