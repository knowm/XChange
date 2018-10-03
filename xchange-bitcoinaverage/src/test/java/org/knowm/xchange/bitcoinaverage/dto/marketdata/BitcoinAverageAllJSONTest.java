package org.knowm.xchange.bitcoinaverage.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** Test BitcoinAverageTicker JSON parsing */
public class BitcoinAverageAllJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoinAverageTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinaverage/dto/marketdata/example-ticker-all.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinAverageTickers bitcoinAverageTicker = mapper.readValue(is, BitcoinAverageTickers.class);

    // Verify that the example data was unmarshalled correctly
    assertTrue(bitcoinAverageTicker.getTickers().containsKey("USD"));
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getLast()).isEqualTo("526.54");
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getAsk()).isEqualTo("527.55");
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getBid()).isEqualTo("525.62");
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getVolume()).isEqualTo("91178.27");
    // assertThat(bitcoinAverageTicker.getTimestamp().toLocaleString()).isEqualTo("16-Apr-2014
    // 6:58:34 PM");
  }
}
