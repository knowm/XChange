package com.xeiam.xchange.bitcointoyou.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTransaction;

/**
 * Test Transaction[] JSON parsing
 * 
 * @author Felipe Micaroni Lalli
 */
public class TradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinToYouTransaction[] transactions = mapper.readValue(is, BitcoinToYouTransaction[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(transactions[0].getDate()).isEqualTo(1412861436L);
    assertThat(transactions[0].getTid()).isEqualTo(25525);
    assertThat(transactions[0].getPrice()).isEqualTo(new BigDecimal("980.28"));
    assertThat(transactions[0].getAmount()).isEqualTo(new BigDecimal("0.0099238"));
    assertThat(transactions[0].getType()).isEqualTo("buy");
    assertThat(transactions[0].getCurrency()).isEqualTo("BTC");
  }
}
