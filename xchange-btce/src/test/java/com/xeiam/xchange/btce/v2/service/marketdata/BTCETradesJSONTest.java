package com.xeiam.xchange.btce.v2.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v2.dto.marketdata.BTCETrade;

/**
 * Test BTCETrade[] JSON parsing
 */
@Deprecated
@Ignore
public class BTCETradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradesJSONTest.class.getResourceAsStream("/v2/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETrade[] BTCETrades = mapper.readValue(is, BTCETrade[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BTCETrades[0].getPrice()).isEqualTo(new BigDecimal("13.07"));
  }
}
