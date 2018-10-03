package org.knowm.xchange.wex.v3.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.wex.v3.WexAdapters;

/** Test WexDepth JSON parsing */
public class WexDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexDepthWrapper bTCEDepthWrapper = mapper.readValue(is, WexDepthWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(
            bTCEDepthWrapper
                .getDepth(WexAdapters.getPair(CurrencyPair.BTC_USD))
                .getAsks()
                .get(0)[0])
        .isEqualTo(new BigDecimal("760.98"));
    assertThat(bTCEDepthWrapper.getDepth(WexAdapters.getPair(CurrencyPair.BTC_USD)).getAsks())
        .hasSize(30);
  }
}
