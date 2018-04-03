package org.knowm.xchange.gatecoin.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinDepthResult;

/** Test GatecoinTicker JSON parsing */
public class DepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        DepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/marketdata/example-depth-data.json");

    ObjectMapper mapper = new ObjectMapper();
    GatecoinDepthResult gatecoinDepthResult = mapper.readValue(is, GatecoinDepthResult.class);
    GatecoinDepth[] gatecoinAsks = gatecoinDepthResult.getAsks();
    GatecoinDepth[] gatecoinBids = gatecoinDepthResult.getBids();

    // Verify that the example data was unmarshalled correctly
    assertThat(gatecoinAsks[0].getPrice()).isEqualTo(new BigDecimal("241.89"));
    assertThat(gatecoinBids[0].getVolume()).isEqualTo(new BigDecimal("10"));
  }
}
