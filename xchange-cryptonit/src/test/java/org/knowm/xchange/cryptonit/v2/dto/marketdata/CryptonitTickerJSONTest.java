package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Test CryptonitTicker JSON parsing */
public class CryptonitTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptonitTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptonit/v2/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptonitTicker cryptonitTicker = mapper.readValue(is, CryptonitTicker.class);
    CryptonitRate cryptonitTickerRate = cryptonitTicker.getRate();

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptonitTickerRate.getLast()).isEqualTo(new BigDecimal("605.997"));
    assertThat(cryptonitTickerRate.getHigh()).isEqualTo(new BigDecimal("610.00000000"));
    assertThat(cryptonitTickerRate.getLow()).isEqualTo(new BigDecimal("572.73768613"));
    assertThat(cryptonitTicker.getVolume().getVolume("BTC"))
        .isEqualTo(new BigDecimal("8.28600851"));
    assertThat(cryptonitTickerRate.getAsk()).isEqualTo(new BigDecimal("604.44900000"));
    assertThat(cryptonitTickerRate.getBid()).isEqualTo(new BigDecimal("584.79532163"));
  }
}
