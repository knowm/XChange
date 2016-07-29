package org.knowm.xchange.coinfloor.dto.streaming.marketdata;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author obsessiveOrange
 */
public class CoinfloorTickerTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorTickerTest.class.getResourceAsStream("/marketdata/example-ticker-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorTicker testObject = mapper.readValue(is, CoinfloorTicker.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(202, testObject.getTag());
    Assert.assertEquals(BigDecimal.valueOf(32000, 2), testObject.getLast());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), testObject.getHigh());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), testObject.getLow());
    Assert.assertEquals(BigDecimal.valueOf(33000, 2), testObject.getAsk());
    Assert.assertEquals(BigDecimal.valueOf(32000, 2), testObject.getBid());
  }

  @Test
  public void testMapping2() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorTickerTest.class.getResourceAsStream("/marketdata/example-ticker-update.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorTicker testObject = mapper.readValue(is, CoinfloorTicker.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), testObject.getLast());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), testObject.getHigh());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), testObject.getLow());
    Assert.assertEquals(BigDecimal.valueOf(31899, 2), testObject.getAsk());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), testObject.getBid());
  }
}
