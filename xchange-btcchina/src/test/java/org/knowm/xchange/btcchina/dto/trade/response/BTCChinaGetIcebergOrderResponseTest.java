package org.knowm.xchange.btcchina.dto.trade.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaIcebergOrder;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaGetIcebergOrderResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCChinaGetIcebergOrderResponse() throws IOException {

    BTCChinaGetIcebergOrderResponse response = mapper.readValue(getClass().getResource("getIcebergOrder.json"),
        BTCChinaGetIcebergOrderResponse.class);
    BTCChinaIcebergOrder io = response.getResult().getIcebergOrder();
    assertEquals(1, io.getId());
    assertEquals("bid", io.getType());
    assertEquals(new BigDecimal("40.00"), io.getPrice());
    assertEquals("BTCCNY", io.getMarket());
    assertEquals(new BigDecimal("12.00000000"), io.getAmount());
    assertEquals(new BigDecimal("12.00000000"), io.getAmountOriginal());
    assertEquals(new BigDecimal("5.00000000"), io.getDisclosedAmount());
    assertEquals(new BigDecimal("0.10"), io.getVariance());
    assertEquals(1405412126L, io.getDate());
    assertEquals("open", io.getStatus());

    BTCChinaOrder o = io.getOrders()[0];
    assertEquals(3301L, o.getId());
    assertEquals("bid", o.getType());
    assertEquals(new BigDecimal("40.00"), o.getPrice());
    assertEquals("CNY", o.getCurrency());
    assertEquals(new BigDecimal("4.67700000"), o.getAmount());
    assertEquals(new BigDecimal("4.67700000"), o.getAmountOriginal());
    assertEquals(1405412126L, o.getDate());
    assertEquals("open", o.getStatus());
  }

}
