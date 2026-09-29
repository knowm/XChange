package org.knowm.xchange.bitcoinaverage.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

/** Test BitcoinAverageTicker JSON parsing */
class BitcoinAverageAllJSONTest {

  @Test
  void unmarshal() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoinAverageTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinaverage/dto/marketdata/example-ticker-all.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinAverageTickers bitcoinAverageTicker = mapper.readValue(is, BitcoinAverageTickers.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcoinAverageTicker.getTickers().containsKey("USD")).isTrue();
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getLast()).isEqualTo("526.54");
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getAsk()).isEqualTo("527.55");
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getBid()).isEqualTo("525.62");
    assertThat(bitcoinAverageTicker.getTickers().get("USD").getVolume()).isEqualTo("91178.27");
    // assertThat(bitcoinAverageTicker.getTimestamp().toLocaleString()).isEqualTo("16-Apr-2014
    // 6:58:34 PM");
  }
}
