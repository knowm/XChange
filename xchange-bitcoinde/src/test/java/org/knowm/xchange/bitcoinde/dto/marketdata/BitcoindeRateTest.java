package org.knowm.xchange.bitcoinde.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author matthewdowney
 */
public class BitcoindeRateTest {

  @Test
  public void testBitcoindeRate() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcoindeRateTest.class.getResourceAsStream("/rate.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeRate bitcoindeRate = mapper.readValue(is, BitcoindeRate.class);

    // Make sure we get what we're expecting
    assertEquals(bitcoindeRate.getRate_weighted(), "225.18347646");
    assertEquals(bitcoindeRate.getRate_weighted_12h(), "224.91338164");
    assertEquals(bitcoindeRate.getRate_weighted_3h(), "225.18347646");
  }
}
