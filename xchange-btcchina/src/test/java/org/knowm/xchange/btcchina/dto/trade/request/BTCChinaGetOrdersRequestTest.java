package org.knowm.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BTCChinaGetOrdersRequestTest {

  @Test
  public void testBTCChinaGetOrdersRequestBooleanStringIntegerIntegerIntegerBoolean() {

    assertEquals("[true,\"BTCCNY\",1000,0,0,false]", new BTCChinaGetOrdersRequest(null, null, null, null, null, null).getParams());
  }

}
