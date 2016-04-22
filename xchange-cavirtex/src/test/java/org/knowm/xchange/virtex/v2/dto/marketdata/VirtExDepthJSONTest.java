package org.knowm.xchange.virtex.v2.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test VirtExDepth JSON parsing
 */
public class VirtExDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExDepthWrapper virtExDepth = mapper.readValue(is, VirtExDepthWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(virtExDepth.getDepth().getAsks().get(0)[0]).isEqualTo(new BigDecimal("500000.000000000"));
  }
}
