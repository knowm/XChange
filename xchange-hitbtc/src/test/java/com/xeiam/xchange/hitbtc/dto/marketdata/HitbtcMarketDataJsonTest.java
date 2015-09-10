package com.xeiam.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcMarketDataJsonTest {

  @Test
  public void testDeserializeTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTicker ticker = mapper.readValue(is, HitbtcTicker.class);

    assertThat(ticker.getAsk()).isEqualTo("609.58");
    assertThat(ticker.getBid()).isEqualTo("608.63");
    assertThat(ticker.getLast()).isEqualTo("609.24");
    assertThat(ticker.getHigh()).isEqualTo("621.81");
    assertThat(ticker.getLow()).isEqualTo("563.4");
    assertThat(ticker.getVolume()).isEqualTo("1232.17");
    assertThat(ticker.getTimetamp()).isEqualTo(1401498463370L);
  }

  @Test
  public void testDeserializeOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-orderbook-data.json");

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