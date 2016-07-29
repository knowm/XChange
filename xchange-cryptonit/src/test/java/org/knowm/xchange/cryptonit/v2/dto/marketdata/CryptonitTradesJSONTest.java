package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test CryptonitTrade[] JSON parsing
 */
public class CryptonitTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptonitTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitTrades = mapper.readValue(is, CryptonitOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptonitTrades.getOrder(268129).getAskAmount()).isEqualTo(new BigDecimal("703.56367800"));
  }
}
