package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

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

    HitbtcOrderLimit[] asks = orderBook.getAsks();
    assertThat(asks).hasSize(5);
    assertThat(asks[0].getPrice()).isEqualTo("4274.81");
    assertThat(asks[0].getSize()).isEqualTo("0.15");

    HitbtcOrderLimit[] bids = orderBook.getBids();
    assertThat(bids).hasSize(5);
    assertThat(bids[2].getPrice()).isEqualTo("4253.86");
    assertThat(bids[2].getSize()).isEqualTo("0.05");
  }
}
