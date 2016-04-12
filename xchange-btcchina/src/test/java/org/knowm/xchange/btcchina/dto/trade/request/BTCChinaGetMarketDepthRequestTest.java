package org.knowm.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BTCChinaGetMarketDepthRequestTest {

  @Test
  public void testBTCChinaGetMarketDepthRequest() {

    assertEquals("[]", new BTCChinaGetMarketDepthRequest(null, null).getParams());
    assertEquals("[1]", new BTCChinaGetMarketDepthRequest(1, null).getParams());
    assertEquals("[10,\"LTCCNY\"]", new BTCChinaGetMarketDepthRequest(null, "LTCCNY").getParams());
    assertEquals("[1,\"LTCCNY\"]", new BTCChinaGetMarketDepthRequest(1, "LTCCNY").getParams());
  }

}
