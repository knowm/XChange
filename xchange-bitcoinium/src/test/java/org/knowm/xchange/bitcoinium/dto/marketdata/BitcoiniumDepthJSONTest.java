package org.knowm.xchange.bitcoinium.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test BitcoiniumDepth JSON parsing */
public class BitcoiniumDepthJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoiniumDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinium/dto/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    BitcoiniumOrderbook bitcoiniumOrderbook = mapper.readValue(is, BitcoiniumOrderbook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcoiniumOrderbook.getBitcoiniumTicker().getVolume())
        .isEqualTo(new BigDecimal("5787"));
    assertThat(bitcoiniumOrderbook.getBids()[0].getVolume()).isEqualTo(new BigDecimal("1.55"));
  }
}
