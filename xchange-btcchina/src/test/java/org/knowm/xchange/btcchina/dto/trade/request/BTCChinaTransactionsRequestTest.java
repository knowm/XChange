package org.knowm.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BTCChinaTransactionsRequestTest {

  @Test
  public void testBTCChinaTransactionsRequest() {

    assertEquals("[\"all\",10,0,0,\"time\"]", new BTCChinaTransactionsRequest(null, null, null, null, null).getParams());
    assertEquals("[\"buybtc\",2,0,0,\"time\"]", new BTCChinaTransactionsRequest("buybtc", 2, null, null, null).getParams());
    assertEquals("[\"all\",100,5,0,\"time\"]", new BTCChinaTransactionsRequest("all", 100, 5, null, null).getParams());
    assertEquals("[\"all\",100,5,0,\"time\"]", new BTCChinaTransactionsRequest("all", 100, 5, null, null).getParams());
    assertEquals("[\"all\",100,5,1,\"time\"]", new BTCChinaTransactionsRequest("all", 100, 5, 1, null).getParams());
    assertEquals("[\"all\",100,5,0,\"id\"]", new BTCChinaTransactionsRequest("all", 100, 5, null, "id").getParams());
    assertEquals("[\"all\",100,5,1,\"id\"]", new BTCChinaTransactionsRequest("all", 100, 5, 1, "id").getParams());
  }

}
