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
public class CoinfloorOrderbookTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorOrderbookTest.class.getResourceAsStream("/marketdata/example-orders-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorOrderbook testObject = mapper.readValue(is, CoinfloorOrderbook.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(201, testObject.getTag());
    Assert.assertEquals(14, testObject.getOrders().size());
    Assert.assertEquals(211077, testObject.getOrders().get(0).getId());
    Assert.assertEquals(BigDecimal.valueOf(32000, 2), testObject.getOrders().get(0).getPrice());
    Assert.assertEquals(BigDecimal.valueOf(9983, 4), testObject.getOrders().get(0).getBaseQty());
  }
}
