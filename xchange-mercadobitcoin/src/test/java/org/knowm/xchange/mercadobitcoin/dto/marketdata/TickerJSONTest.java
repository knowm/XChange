package org.knowm.xchange.mercadobitcoin.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test MercadoBitcoinTicker JSON parsing
 *
 * @author Felipe Micaroni Lalli
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    ObjectMapper mapper = new ObjectMapper();
    MercadoBitcoinTicker mercadoBitcoinTicker = mapper.readValue(is, MercadoBitcoinTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(mercadoBitcoinTicker.getTicker().getLast()).isEqualTo(new BigDecimal("1019.99999"));
    assertThat(mercadoBitcoinTicker.getTicker().getHigh()).isEqualTo(new BigDecimal("1020"));
    assertThat(mercadoBitcoinTicker.getTicker().getLow()).isEqualTo(new BigDecimal("1004.2143"));
    assertThat(mercadoBitcoinTicker.getTicker().getVol()).isEqualTo(new BigDecimal("6.90157391"));
    assertThat(mercadoBitcoinTicker.getTicker().getBuy()).isEqualTo(new BigDecimal("1019.99999"));
    assertThat(mercadoBitcoinTicker.getTicker().getSell()).isEqualTo(new BigDecimal("1020"));
    assertThat(mercadoBitcoinTicker.getTicker().getDate()).isEqualTo(1417226432L);
  }

}
