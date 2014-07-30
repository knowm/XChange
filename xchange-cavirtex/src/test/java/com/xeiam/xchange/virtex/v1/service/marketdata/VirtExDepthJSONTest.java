package com.xeiam.xchange.virtex.v1.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.virtex.v1.dto.marketdata.VirtExDepth;

/**
 * Test VirtExDepth JSON parsing
 */
public class VirtExDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExDepth virtExDepth = mapper.readValue(is, VirtExDepth.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(virtExDepth.getAsks().get(0)[0]).isEqualTo(new BigDecimal("16.905360000"));
  }
}
