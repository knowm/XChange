package org.knowm.xchange.btcturk.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author semihunaldi Test BTCTurkTrade[] JSON parsing
 */
public class BTCTurkTradesTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTradesTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkTrades[] trades = mapper.readValue(is, BTCTurkTrades[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(DateUtils.toUTCString(trades[0].getDate())).isEqualTo("1970-01-18 21:51:16 GMT");
    assertThat(trades[0].getTid()).isEqualTo(new BigDecimal("636830731788787572"));
    assertThat(trades[0].getPrice()).isEqualTo(new BigDecimal("19800"));
    assertThat(trades[0].getAmount()).isEqualTo(new BigDecimal("0.14187336"));
  }
}
