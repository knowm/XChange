package org.knowm.xchange.bitcoinaverage.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test BitcoinAverageTicker JSON parsing */
public class BitcoinAverageTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoinAverageTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinaverage/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinAverageTicker BitcoinAverageTicker = mapper.readValue(is, BitcoinAverageTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(BitcoinAverageTicker.getLast()).isEqualTo(new BigDecimal("629.45"));
    assertThat(BitcoinAverageTicker.getBid()).isEqualTo(new BigDecimal("628.2"));
    assertThat(BitcoinAverageTicker.getAsk()).isEqualTo(new BigDecimal("631.21"));
    assertThat(BitcoinAverageTicker.getVolume()).isEqualTo(new BigDecimal("118046.63"));
  }
}
