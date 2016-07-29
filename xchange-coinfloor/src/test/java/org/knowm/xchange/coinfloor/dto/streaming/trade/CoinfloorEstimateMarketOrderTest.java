package org.knowm.xchange.coinfloor.dto.streaming.trade;

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
public class CoinfloorEstimateMarketOrderTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorEstimateMarketOrderTest.class.getResourceAsStream("/trade/example-estimateMarketOrder-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorEstimateMarketOrder testObject = mapper.readValue(is, CoinfloorEstimateMarketOrder.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(304, testObject.getTag());
    Assert.assertEquals(BigDecimal.valueOf(10000, 4), testObject.getBaseQty());
    Assert.assertEquals(BigDecimal.valueOf(33000, 2), testObject.getCounterQty());
  }
}
