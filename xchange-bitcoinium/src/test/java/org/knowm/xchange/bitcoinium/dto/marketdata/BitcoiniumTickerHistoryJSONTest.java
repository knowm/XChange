package org.knowm.xchange.bitcoinium.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test BitcoiniumTickerHistory JSON parsing */
public class BitcoiniumTickerHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoiniumTickerHistoryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinium/dto/marketdata/example-ticker-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoiniumTickerHistory bitcoiniumTickerHistory =
        mapper.readValue(is, BitcoiniumTickerHistory.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcoiniumTickerHistory.getCondensedTickers()[0].getLast())
        .isEqualTo(new BigDecimal("514.9"));
  }
}
