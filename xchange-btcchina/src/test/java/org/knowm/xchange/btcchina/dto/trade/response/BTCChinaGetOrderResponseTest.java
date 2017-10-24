package org.knowm.xchange.btcchina.dto.trade.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaGetOrderResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCChinaGetOrderResponse() throws IOException {

    BTCChinaGetOrderResponse response = mapper.readValue(getClass().getResource("getOrder.json"), BTCChinaGetOrderResponse.class);
    BTCChinaOrder order = response.getResult().getOrder();
    assertOrder(order);
    assertNull(order.getDetails());
  }

  @Test
  public void testBTCChinaGetOrderResponseWithdetail() throws IOException {

    BTCChinaGetOrderResponse response = mapper.readValue(getClass().getResource("getOrder-withdetail.json"), BTCChinaGetOrderResponse.class);
    BTCChinaOrder order = response.getResult().getOrder();
    assertOrder(order);
    assertEquals(1403170822L, order.getDetails()[0].getDateline());
    assertEquals(new BigDecimal("3811.69"), order.getDetails()[0].getPrice());
    assertEquals(new BigDecimal("0.031"), order.getDetails()[0].getAmount());
  }

  private void assertOrder(BTCChinaOrder order) {

    assertEquals(12345678, order.getId());
    assertEquals("ask", order.getType());
    assertEquals(new BigDecimal("3811.69"), order.getPrice());
    assertEquals("CNY", order.getCurrency());
    assertEquals(new BigDecimal("0.00000000"), order.getAmount());
    assertEquals(new BigDecimal("0.03100000"), order.getAmountOriginal());
    assertEquals(1403170821L, order.getDate());
    assertEquals("closed", order.getStatus());
  }

}
