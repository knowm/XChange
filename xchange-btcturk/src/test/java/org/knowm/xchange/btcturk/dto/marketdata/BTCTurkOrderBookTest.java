package org.knowm.xchange.btcturk.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** Created by semihunaldi on 26/11/2017 */
public class BTCTurkOrderBookTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/marketdata/example-order-book-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkOrderBook btcTurkOrderBook = mapper.readValue(is, BTCTurkOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(btcTurkOrderBook.getTimestamp().getTime()).isEqualTo(1511729132L);
    assertThat(btcTurkOrderBook.getBids().size()).isEqualTo(30);
    assertThat(btcTurkOrderBook.getAsks().size()).isEqualTo(30);
    assertThat(btcTurkOrderBook.getBids().get(0).get(0))
        .isEqualTo(new BigDecimal("38620.00000000"));
    assertThat(btcTurkOrderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.12500000"));
  }
}
