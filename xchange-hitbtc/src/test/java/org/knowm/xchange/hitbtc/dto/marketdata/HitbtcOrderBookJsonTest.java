package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcOrderBookJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcOrderBookJsonTest.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    HitbtcOrderBook orderBook = mapper.readValue(is, HitbtcOrderBook.class);

    BigDecimal[][] asks = orderBook.getAsks();
    assertThat(asks).hasSize(3);
    assertThat(asks[0][0]).isEqualTo("609.58");
    assertThat(asks[0][1]).isEqualTo("1.23");

    BigDecimal[][] bids = orderBook.getBids();
    assertThat(bids).hasSize(3);
    assertThat(bids[2][0]).isEqualTo("1");
    assertThat(bids[2][1]).isEqualTo("10100.01");
  }
}
