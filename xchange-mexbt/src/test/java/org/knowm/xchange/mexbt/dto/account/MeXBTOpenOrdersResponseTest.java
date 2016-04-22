package org.knowm.xchange.mexbt.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTOpenOrdersResponseTest {

  @Test
  public void testMeXBTOpenOrdersResponse() throws JsonParseException, JsonMappingException, IOException {
    MeXBTOpenOrdersResponse openOrdersResponse = new ObjectMapper().readValue(MeXBTOpenOrdersResponseTest.class.getResource("orders.json"),
        MeXBTOpenOrdersResponse.class);
    assertEquals(1415353755380L, openOrdersResponse.getDateTimeUtc().getTime());
    MeXBTOpenOrdersInfo[] openOrdersInfo = openOrdersResponse.getOpenOrdersInfo();
    assertEquals("BTCMXN", openOrdersInfo[0].getIns());

    MeXBTOpenOrder[] openOrders = openOrdersInfo[0].getOpenOrders();
    assertEquals(63710062L, openOrders[0].getServerOrderId());
    assertEquals(12, openOrders[0].getAccountId());
    assertEquals(new BigDecimal("100"), openOrders[0].getPrice());
    assertEquals(new BigDecimal("1"), openOrders[0].getQtyTotal());
    assertEquals(new BigDecimal("1"), openOrders[0].getQtyRemaining());
    assertEquals(1415353750631L, openOrders[0].getReceiveTime().getTime());
    assertEquals(0, openOrders[0].getSide());
  }

}
