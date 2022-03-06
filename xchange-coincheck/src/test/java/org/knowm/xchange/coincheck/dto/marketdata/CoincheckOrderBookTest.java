package org.knowm.xchange.coincheck.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.coincheck.CoincheckTestUtil;

/** Tests CoincheckOrderBook JSON parsing */
public class CoincheckOrderBookTest {

  @Test
  public void testUnmarshal() throws IOException {
    // Read in the JSON from the example resources.
    CoincheckOrderBook orderBook =
        CoincheckTestUtil.load(
            "dto/marketdata/example-order-books-data.json", CoincheckOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo(new BigDecimal("5052305.0"));
    assertThat(orderBook.getAsks().get(0).get(1)).isEqualTo(new BigDecimal("0.1"));
    assertThat(orderBook.getAsks().get(1).get(0)).isEqualTo(new BigDecimal("5052961.0"));
    assertThat(orderBook.getAsks().get(1).get(1)).isEqualTo(new BigDecimal("0.03686658"));

    assertThat(orderBook.getBids().get(0).get(0)).isEqualTo(new BigDecimal("5050174.0"));
    assertThat(orderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.02"));
    assertThat(orderBook.getBids().get(1).get(0)).isEqualTo(new BigDecimal("5050002.0"));
    assertThat(orderBook.getBids().get(1).get(1)).isEqualTo(new BigDecimal("0.005"));
  }
}
