package org.knowm.xchange.btce.v3.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.btce.v3.BTCEAdapters;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BTCEDepth JSON parsing
 */
public class BTCEDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCEDepthJSONTest.class.getResourceAsStream("/v3/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCEDepthWrapper bTCEDepthWrapper = mapper.readValue(is, BTCEDepthWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bTCEDepthWrapper.getDepth(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).getAsks().get(0)[0]).isEqualTo(new BigDecimal("760.98"));
    assertThat(bTCEDepthWrapper.getDepth(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).getAsks()).hasSize(30);
  }
}
