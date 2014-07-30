package com.xeiam.xchange.btce.v3.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.currency.CurrencyPair;

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
    assertThat(bTCEDepthWrapper.getDepth(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getAsks().get(0)[0]).isEqualTo(new BigDecimal("760.98"));
    assertThat(bTCEDepthWrapper.getDepth(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getAsks()).hasSize(30);
  }
}
