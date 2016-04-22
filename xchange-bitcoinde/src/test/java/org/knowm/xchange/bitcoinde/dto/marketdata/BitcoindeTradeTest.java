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
public class BitcoindeTradeTest {

  @Test
  public void testBitcoindeTrade() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoindeTradeTest.class.getResourceAsStream("/trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeTrade[] bitcoindeTrade = mapper.readValue(is, BitcoindeTrade[].class);

    // Make sure trade values are correct
    assertEquals(bitcoindeTrade[0].getDate(), 1428255708L);
    assertEquals(bitcoindeTrade[0].getPrice(), new BigDecimal("236.61"));
    assertEquals(bitcoindeTrade[0].getAmount(), "0.78054655");
    assertEquals(bitcoindeTrade[0].getTid(), 1210152L);
  }
}
