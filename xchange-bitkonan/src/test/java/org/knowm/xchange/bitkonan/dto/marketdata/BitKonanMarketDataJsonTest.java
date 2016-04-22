package org.knowm.xchange.bitkonan.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanMarketDataJsonTest {

  @Test
  public void testDeserializeTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitKonanMarketDataJsonTest.class.getResourceAsStream("/marketdata/bitkonan-example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    BitKonanTicker ticker = mapper.readValue(is, BitKonanTicker.class);

    assertThat(ticker.getAsk()).isEqualTo("609.58");
    assertThat(ticker.getBid()).isEqualTo("608.63");
    assertThat(ticker.getLast()).isEqualTo("609.24");
    assertThat(ticker.getHigh()).isEqualTo("621.81");
    assertThat(ticker.getLow()).isEqualTo("563.4");
    assertThat(ticker.getVolume()).isEqualTo("1232.17");
  }

  @Test
  public void testDeserializeOrderBook() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitKonanMarketDataJsonTest.class.getResourceAsStream("/marketdata/bitkonan-example-orderbook-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitKonanOrderBook orderBook = mapper.readValue(is, BitKonanOrderBook.class);

    BitKonanOrderBookElement[] asks = orderBook.getAsks();
    assertThat(asks).hasSize(10);
    assertTrue(asks[0].getVolume().compareTo(new BigDecimal("0.46557")) == 0);
    assertTrue(asks[1].getVolume().compareTo(new BigDecimal("0.5011")) == 0);

    BitKonanOrderBookElement[] bids = orderBook.getBids();
    assertThat(bids).hasSize(10);
    assertTrue(bids[0].getVolume().compareTo(new BigDecimal("0.04455")) == 0);
    assertTrue(bids[1].getVolume().compareTo(new BigDecimal("3")) == 0);
  }
}