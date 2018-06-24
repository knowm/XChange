package org.knowm.xchange.btcturk.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author semihunaldi Test BTCTurkTrade[] JSON parsing */
public class BTCTurkTradeTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTradeTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkTrade[] trades = mapper.readValue(is, BTCTurkTrade[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(trades[0].getDate()).isEqualTo(1511728478L);
    assertThat(trades[0].getTid()).isEqualTo(1);
    assertThat(trades[0].getPrice()).isEqualTo(new BigDecimal("38880"));
    assertThat(trades[0].getAmount()).isEqualTo(new BigDecimal("0.09967147"));
  }
}
