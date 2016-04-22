package org.knowm.xchange.btcchina.dto.account.request;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class BTCChinaRequestWithdrawalRequestTest {

  @Test
  public void testBTCChinaRequestWithdrawalRequestStringBigDecimal() {

    BTCChinaRequestWithdrawalRequest request = new BTCChinaRequestWithdrawalRequest("BTC", new BigDecimal(Long.MAX_VALUE));
    assertEquals("[\"BTC\"," + Long.MAX_VALUE + "]", request.getParams());
  }

}
