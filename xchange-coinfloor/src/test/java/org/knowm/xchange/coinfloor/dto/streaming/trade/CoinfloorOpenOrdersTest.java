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
public class CoinfloorOpenOrdersTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorOpenOrdersTest.class.getResourceAsStream("/trade/example-openOrders-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorOpenOrders testObject = mapper.readValue(is, CoinfloorOpenOrders.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(301, testObject.getTag());
    Assert.assertEquals(211118, testObject.getOrders().get(0).getId());
    Assert.assertEquals(CoinfloorCurrency.BTC, testObject.getOrders().get(0).getBase());
    Assert.assertEquals(CoinfloorCurrency.GBP, testObject.getOrders().get(0).getCounter());
    Assert.assertEquals(BigDecimal.valueOf(10000, 2), testObject.getOrders().get(0).getPrice());
    Assert.assertEquals(BigDecimal.valueOf(10000, 4), testObject.getOrders().get(0).getBaseQty());
  }
}
