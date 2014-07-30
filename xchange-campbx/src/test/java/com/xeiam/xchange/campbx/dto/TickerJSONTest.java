package com.xeiam.xchange.campbx.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.campbx.dto.marketdata.CampBXTicker;

/**
 * Test BitstampTicker JSON parsing
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    CampBXTicker campBXTicker = mapper.readValue(is, CampBXTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(campBXTicker.getLast()).isEqualTo(new BigDecimal("13.30"));
    assertThat(campBXTicker.getBid()).isEqualTo(new BigDecimal("13.30"));
    assertThat(campBXTicker.getAsk()).isEqualTo(new BigDecimal("13.52"));
  }

}
