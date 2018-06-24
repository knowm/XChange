package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test CryptonitDepth JSON parsing */
public class CryptonitDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptonitDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit/v2/dto/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitDepth = mapper.readValue(is, CryptonitOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptonitDepth.getOrder(60751).getAskAmount())
        .isEqualTo(new BigDecimal("8.74000000"));
  }
}
