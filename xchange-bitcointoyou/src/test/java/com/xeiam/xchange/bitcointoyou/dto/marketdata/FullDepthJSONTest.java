package com.xeiam.xchange.bitcointoyou.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouOrderBook;

/**
 * Test BitcoinToYou Full Depth JSON parsing
 * 
 * @author Felipe Micaroni Lalli
 */
public class FullDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = FullDepthJSONTest.class.getResourceAsStream("/marketdata/example-full-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouOrderBook orderBook = mapper.readValue(is, BitcoinToYouOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getBids().get(0).get(0)).isEqualTo(new BigDecimal("956.72"));
    assertThat(orderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.00055518"));
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo(new BigDecimal("4955.3"));
    assertThat(orderBook.getAsks().get(0).get(1)).isEqualTo(new BigDecimal("0.01575303"));
  }
}
