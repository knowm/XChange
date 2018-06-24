package org.knowm.xchange.btcturk.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Created by semihunaldi on 26/11/2017 */
public class BTCTurkOHLCTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-ohlc-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkOHLC[] btcTurkOHLCs = mapper.readValue(is, BTCTurkOHLC[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(btcTurkOHLCs[0].getAverage()).isEqualTo("38029.55");
    assertThat(btcTurkOHLCs[0].getClose()).isEqualTo(new BigDecimal("38733"));
    assertThat(btcTurkOHLCs[0].getDailyChangePercentage()).isEqualTo(new BigDecimal("3053"));
  }
}
