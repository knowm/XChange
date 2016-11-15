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
 * Test BTCETrade[] JSON parsing
 */
public class BTCETradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradesJSONTest.class.getResourceAsStream("/v3/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETradesWrapper bTCETradesWrapper = mapper.readValue(is, BTCETradesWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bTCETradesWrapper.getTrades(BTCEAdapters.getPair(CurrencyPair.BTC_USD))[0].getPrice()).isEqualTo(new BigDecimal("758.5"));
    assertThat(bTCETradesWrapper.getTrades(BTCEAdapters.getPair(CurrencyPair.BTC_USD)).length).isEqualTo(100);
  }
}
