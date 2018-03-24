package org.knowm.xchange.gatecoin.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTickerResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test GatecoinTicker JSON parsing
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    GatecoinTickerResult gatecoinTickerResult = mapper.readValue(is, GatecoinTickerResult.class);
    GatecoinTicker[] gatecoinTicker = gatecoinTickerResult.getTicker();

    // Verify that the example data was unmarshalled correctly
    assertThat(gatecoinTicker[0].getLast()).isEqualTo(new BigDecimal("241.58"));
    assertThat(gatecoinTicker[0].getHigh()).isEqualTo(new BigDecimal("243.17"));
  }

}
