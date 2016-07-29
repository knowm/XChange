package org.knowm.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class BTCChinaBuyOrderRequestTest {

  @Test
  public void testLimitPriceOrder() {

    BTCChinaBuyOrderRequest request = new BTCChinaBuyOrderRequest(new BigDecimal("0.01"), new BigDecimal("1"), "BTCCNY");
    assertEquals("buyOrder2", request.getMethod());
    assertEquals("[0.01,1,\"BTCCNY\"]", request.getParams());
  }

  @Test
  public void testMarketOrder() {

    BTCChinaBuyOrderRequest request = new BTCChinaBuyOrderRequest(null, new BigDecimal("1"), "BTCCNY");
    assertEquals("buyOrder2", request.getMethod());
    assertEquals("[null,1,\"BTCCNY\"]", request.getParams());
  }

}
