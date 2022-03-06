package org.knowm.xchange.coincheck.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.coincheck.CoincheckTestUtil;

/** Tests CoincheckTicker JSON parsing */
public class CoincheckTickerTest {

  @Test
  public void testUnmarshal() throws IOException {
    // Read in the JSON from the example resources
    CoincheckTicker ticker =
        CoincheckTestUtil.load("dto/marketdata/example-ticker-data.json", CoincheckTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("5052540.0"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("5053001.0"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("5053397.0"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("5057094.0"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("4776069.0"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("2515.83885078"));
    assertThat(ticker.getTimestamp()).isEqualTo(1644249683L);
  }
}
