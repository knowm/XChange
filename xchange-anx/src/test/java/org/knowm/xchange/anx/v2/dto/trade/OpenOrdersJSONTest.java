package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/** Test ANXOpenOrders JSON parsing */
public class OpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        OpenOrdersJSONTest.class.getResourceAsStream("/v2/trade/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ANXOpenOrder[] anxOpenOrders = mapper.readValue(is, ANXOpenOrder[].class);

    // System.out.println(new Date(anxOpenOrders[0].getTimestamp()));

    // Verify that the example data was unmarshalled correctly
    Assert.assertEquals("7eecf4b2-5785-4500-a5d4-f3f8c924395c", anxOpenOrders[1].getOid());
    Assert.assertEquals("BTC", anxOpenOrders[1].getItem());
    Assert.assertEquals("HKD", anxOpenOrders[1].getCurrency());
    Assert.assertEquals("bid", anxOpenOrders[1].getType());
    Assert.assertEquals("BTC", anxOpenOrders[1].getAmount().getCurrency());
    Assert.assertEquals(new BigDecimal("10.00000000"), anxOpenOrders[1].getAmount().getValue());

    Assert.assertEquals(new BigDecimal("412.34567"), anxOpenOrders[0].getPrice().getValue());
    Assert.assertEquals(new BigDecimal("212.34567"), anxOpenOrders[1].getPrice().getValue());
    Assert.assertEquals("open", anxOpenOrders[1].getStatus());
  }
}
