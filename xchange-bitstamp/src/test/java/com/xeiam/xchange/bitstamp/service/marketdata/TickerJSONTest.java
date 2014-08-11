package com.xeiam.xchange.bitstamp.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampTicker;

/**
 * Test BitstampTicker JSON parsing
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    BitstampTicker bitstampTicker = mapper.readValue(is, BitstampTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitstampTicker.getLast()).isEqualTo(new BigDecimal("589.21"));
    assertThat(bitstampTicker.getHigh()).isEqualTo(new BigDecimal("592.67"));
    assertThat(bitstampTicker.getLow()).isEqualTo(new BigDecimal("585.21"));
    assertThat(bitstampTicker.getVolume()).isEqualTo(new BigDecimal("2176.61519264"));
    assertThat(bitstampTicker.getVwap()).isEqualTo(new BigDecimal("588.28"));
    assertThat(bitstampTicker.getBid()).isEqualTo(new BigDecimal("586.88"));
    assertThat(bitstampTicker.getAsk()).isEqualTo(new BigDecimal("589.20"));
    assertThat(bitstampTicker.getTimestamp()).isEqualTo(1407602821L);
  }

}
