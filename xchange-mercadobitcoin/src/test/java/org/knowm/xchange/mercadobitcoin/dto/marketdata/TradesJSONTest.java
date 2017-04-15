package org.knowm.xchange.mercadobitcoin.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    MercadoBitcoinTransaction[] transactions = mapper.readValue(is, MercadoBitcoinTransaction[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(transactions[0].getDate()).isEqualTo(1416854719L);
    assertThat(transactions[0].getTid()).isEqualTo(98519);
    assertThat(transactions[0].getPrice()).isEqualTo(new BigDecimal("1015"));
    assertThat(transactions[0].getAmount()).isEqualTo(new BigDecimal("1"));
    assertThat(transactions[0].getType()).isEqualTo("buy");

  }
}
