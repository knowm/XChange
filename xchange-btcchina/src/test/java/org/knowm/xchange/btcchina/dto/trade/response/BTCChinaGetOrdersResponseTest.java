package org.knowm.xchange.btcchina.dto.trade.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaGetOrdersResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testSingleMarket() throws IOException {

    BTCChinaGetOrdersResponse response = mapper.readValue(getClass().getResourceAsStream("getOrders-single-market.json"),
        BTCChinaGetOrdersResponse.class);
    assertEquals("1", response.getId());
    assertEquals(0, response.getResult().getOrdersArray().length);
    assertEquals(1407054781L, response.getResult().getDate());
  }

  @Test
  public void testAllMarket() throws IOException {

    BTCChinaGetOrdersResponse response = mapper.readValue(getClass().getResourceAsStream("getOrders-all-market.json"),
        BTCChinaGetOrdersResponse.class);
    assertEquals("4", response.getId());
    assertEquals(0, response.getResult().get("order_btccny").length);
    assertEquals(0, response.getResult().get("order_ltccny").length);
    assertEquals(0, response.getResult().get("order_ltcbtc").length);
    assertEquals(1407054786L, response.getResult().getDate());
  }

}
