package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test CryptonitTrade[] JSON parsing */
public class CryptonitTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptonitTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit/v2/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitOrders cryptonitTrades = mapper.readValue(is, CryptonitOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptonitTrades.getOrder(268129).getAskAmount())
        .isEqualTo(new BigDecimal("703.56367800"));
  }
}
