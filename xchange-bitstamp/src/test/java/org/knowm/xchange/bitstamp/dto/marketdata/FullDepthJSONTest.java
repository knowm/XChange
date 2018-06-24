package org.knowm.xchange.bitstamp.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test BitStamp Full Depth JSON parsing */
public class FullDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        FullDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampOrderBook orderBook = mapper.readValue(is, BitstampOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getBids().get(0).get(0)).isEqualTo(new BigDecimal("123.09"));
    assertThat(orderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.16248274"));
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo(new BigDecimal("123.39"));
    assertThat(orderBook.getAsks().get(0).get(1)).isEqualTo(new BigDecimal("0.60466812"));
    assertThat(orderBook.getTimestamp().getTime()).isEqualTo(1378816304000L);
  }
}
