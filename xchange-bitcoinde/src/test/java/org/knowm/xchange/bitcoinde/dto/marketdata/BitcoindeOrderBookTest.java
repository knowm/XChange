package org.knowm.xchange.bitcoinde.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author matthewdowney
 */
public class BitcoindeOrderBookTest {

  @Test
  public void testBitcoindeOrderBook() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoindeOrderBookTest.class.getResourceAsStream("/orderbook.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeOrderBook bitcoindeOrderBook = mapper.readValue(is, BitcoindeOrderBook.class);

    // Make sure asks are correct
    assertEquals(bitcoindeOrderBook.getAsks()[0][0], new BigDecimal("224.9"));
    assertEquals(bitcoindeOrderBook.getAsks()[0][1], new BigDecimal("2.48889"));

    // Make sure bids are correct
    assertEquals(bitcoindeOrderBook.getBids()[0][0], new BigDecimal("222.5"));
    assertEquals(bitcoindeOrderBook.getBids()[0][1], new BigDecimal("0.35"));
  }
}
