package org.knowm.xchange.campbx.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.campbx.dto.marketdata.CampBXOrderBook;

/** Test BitStamp Full Depth JSON parsing */
public class FullDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        FullDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/campbx/marketdata/example-full-depth-data.json");

    ObjectMapper mapper = new ObjectMapper();
    CampBXOrderBook orderBook = mapper.readValue(is, CampBXOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getBids().get(0).get(0)).isEqualTo(new BigDecimal("13.3"));
    assertThat(orderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.00021609"));
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo(new BigDecimal("99.99"));
    assertThat(orderBook.getAsks().get(0).get(1)).isEqualTo(new BigDecimal("0.10000000"));
  }
}
