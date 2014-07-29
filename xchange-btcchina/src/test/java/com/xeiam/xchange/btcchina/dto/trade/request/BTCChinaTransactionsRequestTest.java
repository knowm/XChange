package com.xeiam.xchange.btcchina.dto.trade.request;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Joe Zhou
 */
public class BTCChinaTransactionsRequestTest {

  @Test
  public void testBTCChinaTransactionsRequestStringIntegerInteger() {

    assertEquals("[\"all\",10,0]", new BTCChinaTransactionsRequest(null, null, null).getParams());
    assertEquals("[\"buybtc\",2,0]", new BTCChinaTransactionsRequest("buybtc", 2, null).getParams());
    assertEquals("[\"all\",100,5]", new BTCChinaTransactionsRequest("all", 100, 5).getParams());
  }

}
