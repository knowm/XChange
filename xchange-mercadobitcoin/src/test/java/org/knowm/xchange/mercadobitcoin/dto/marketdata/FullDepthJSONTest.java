package org.knowm.xchange.mercadobitcoin.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/**
 * Test Mercado Bitcoin Full Depth JSON parsing
 *
 * @author Felipe Micaroni Lalli
 */
public class FullDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        FullDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/mercadobitcoin/dto/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MercadoBitcoinOrderBook orderBook = mapper.readValue(is, MercadoBitcoinOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getBids().get(0).get(0)).isEqualTo(new BigDecimal("1004.16826"));
    assertThat(orderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.16614"));
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo(new BigDecimal("1019.99999"));
    assertThat(orderBook.getAsks().get(0).get(1)).isEqualTo(new BigDecimal("0.7"));
  }
}
