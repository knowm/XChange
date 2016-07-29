package org.knowm.xchange.coinfloor.dto.streaming.trade;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */
public class CoinfloorCancelOrderTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorCancelOrderTest.class.getResourceAsStream("/trade/example-cancelOrder-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorCancelOrder testObject = mapper.readValue(is, CoinfloorCancelOrder.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(303, testObject.getTag());
    Assert.assertEquals(CoinfloorCurrency.BTC, testObject.getBase());
    Assert.assertEquals(CoinfloorCurrency.GBP, testObject.getCounter());
    Assert.assertEquals(BigDecimal.valueOf(5000, 2), testObject.getPrice());
    Assert.assertEquals(BigDecimal.valueOf(10000, 4), testObject.getQuantity());
  }
}
