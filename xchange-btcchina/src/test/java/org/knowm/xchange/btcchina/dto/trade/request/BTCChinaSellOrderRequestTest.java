package org.knowm.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class BTCChinaSellOrderRequestTest {

  @Test
  public void testLimitPriceOrder() {

    BTCChinaSellOrderRequest request = new BTCChinaSellOrderRequest(new BigDecimal("0.01"), new BigDecimal("1"), "BTCCNY");
    assertEquals("sellOrder2", request.getMethod());
    assertEquals("[0.01,1,\"BTCCNY\"]", request.getParams());
  }

  @Test
  public void testMarketOrder() {

    BTCChinaSellOrderRequest request = new BTCChinaSellOrderRequest(null, new BigDecimal("1"), "BTCCNY");
    assertEquals("sellOrder2", request.getMethod());
    assertEquals("[null,1,\"BTCCNY\"]", request.getParams());
  }

}
