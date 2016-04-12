package org.knowm.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class BTCChinaOrderRequestTest {

  @Test
  public void testBTCChinaOrderRequest() {

    BTCChinaOrderRequest request;

    request = new BTCChinaOrderRequest("buyOrder2", new BigDecimal("0.01000000"), new BigDecimal("0.00010000"), "BTCCNY");
    assertEquals("[0.01,0.0001,\"BTCCNY\"]", request.getParams());

    request = new BTCChinaOrderRequest("buyOrder2", new BigDecimal("0.01000000"), new BigDecimal("0.00100000"), "LTCCNY");
    assertEquals("[0.01,0.001,\"LTCCNY\"]", request.getParams());

    request = new BTCChinaOrderRequest("buyOrder2", new BigDecimal("0.00010000"), new BigDecimal("0.00100000"), "LTCBTC");
    assertEquals("[0.0001,0.001,\"LTCBTC\"]", request.getParams());
  }

}
