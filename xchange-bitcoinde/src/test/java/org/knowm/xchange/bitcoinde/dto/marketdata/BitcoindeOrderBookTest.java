package org.knowm.xchange.bitcoinde.dto.marketdata;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author matthewdowney */
public class BitcoindeOrderBookTest {

  @Test
  public void testBitcoindeOrderBook()
      throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeOrderBookTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/dto/orderbook.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeOrderbookWrapper.class);

    // Make sure asks are correct
    assertEquals(
        bitcoindeOrderBook.getBitcoindeOrders().getAsks()[0].getPrice(), new BigDecimal("2461.61"));
    assertEquals(
        bitcoindeOrderBook.getBitcoindeOrders().getAsks()[0].getAmount(),
        new BigDecimal("0.0406218"));

    // Make sure bids are correct
    assertEquals(
        bitcoindeOrderBook.getBitcoindeOrders().getBids()[0].getPrice(), new BigDecimal("1200"));
    assertEquals(
        bitcoindeOrderBook.getBitcoindeOrders().getBids()[0].getAmount(), new BigDecimal("8.333"));
  }
}
