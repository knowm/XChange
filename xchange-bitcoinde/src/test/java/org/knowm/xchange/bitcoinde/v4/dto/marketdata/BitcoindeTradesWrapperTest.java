package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class BitcoindeTradesWrapperTest {

  @Test
  public void testBitcoindeTradesWrapper() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeTradesWrapperTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/trades.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeTradesWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeTradesWrapper.class);

    // Make sure trade values are correct
    final BitcoindeTrade[] trades = bitcoindeTradesWrapper.getTrades();

    assertThat(trades[0].getDate()).isEqualTo("2017-07-22T12:14:14+02:00");
    assertThat(trades[0].getPrice()).isEqualByComparingTo("2391.48");
    assertThat(trades[0].getAmount()).isEqualByComparingTo("0.90000000");
    assertThat(trades[0].getTid()).isEqualTo(2844384L);
  }
}
