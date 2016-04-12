package org.knowm.xchange.bitcurex.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BitcurexDepth JSON parsing
 */
public class BitcurexDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexDepth bitcurexDepth = mapper.readValue(is, BitcurexDepth.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcurexDepth.getAsks().get(0)[0]).isEqualTo(new BigDecimal("70.00000000"));
  }
}
