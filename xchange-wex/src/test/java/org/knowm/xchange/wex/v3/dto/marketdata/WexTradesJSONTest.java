package org.knowm.xchange.wex.v3.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.wex.v3.WexAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test WexTrade[] JSON parsing
 */
public class WexTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WexTradesJSONTest.class.getResourceAsStream("/v3/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexTradesWrapper bTCETradesWrapper = mapper.readValue(is, WexTradesWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bTCETradesWrapper.getTrades(WexAdapters.getPair(CurrencyPair.BTC_USD))[0].getPrice()).isEqualTo(new BigDecimal("758.5"));
    assertThat(bTCETradesWrapper.getTrades(WexAdapters.getPair(CurrencyPair.BTC_USD)).length).isEqualTo(100);
  }
}
