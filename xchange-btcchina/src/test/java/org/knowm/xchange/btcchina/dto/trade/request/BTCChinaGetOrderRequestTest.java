package org.knowm.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BTCChinaGetOrderRequestTest {

  @Test
  public void testBTCChinaGetOrderRequestIntStringBoolean() {

    assertEquals("[1,\"BTCCNY\",false]", new BTCChinaGetOrderRequest(1, "BTCCNY", null).getParams());
    assertEquals("[1,\"BTCCNY\",false]", new BTCChinaGetOrderRequest(1, "BTCCNY", Boolean.FALSE).getParams());
    assertEquals("[1,\"BTCCNY\",true]", new BTCChinaGetOrderRequest(1, "BTCCNY", Boolean.TRUE).getParams());
  }

}
