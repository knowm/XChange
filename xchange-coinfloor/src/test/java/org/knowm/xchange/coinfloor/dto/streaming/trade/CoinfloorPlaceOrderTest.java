package org.knowm.xchange.coinfloor.dto.streaming.trade;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author obsessiveOrange
 */
public class CoinfloorPlaceOrderTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorPlaceOrderTest.class.getResourceAsStream("/trade/example-placeOrder-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorPlaceOrder testObject = mapper.readValue(is, CoinfloorPlaceOrder.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(302, testObject.getTag());
    Assert.assertEquals(211120, testObject.getId());
  }
}
