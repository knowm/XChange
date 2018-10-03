package org.knowm.xchange.bitcoinde.dto.marketdata;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class BitcoindeTradesTest {

  @Test
  public void testBitcoindeOrderBook()
      throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeTradesTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/dto/trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeTradesWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeTradesWrapper.class);
    System.out.println("bitcoindeTradesWrapper = " + bitcoindeTradesWrapper);

    // Make sure trade values are correct

    BitcoindeTrade[] trades = bitcoindeTradesWrapper.getTrades();

    assertEquals(trades[0].getDate(), 1500718454L);
    assertEquals(trades[0].getPrice(), new BigDecimal("2391.48"));
    assertEquals(trades[0].getAmount(), new BigDecimal("0.90000000"));
    assertEquals(trades[0].getTid(), 2844384);
  }
}
