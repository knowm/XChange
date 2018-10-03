package org.knowm.xchange.btctrade.dto.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;

public class BTCTradeBalanceTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testError() throws IOException {

    BTCTradeBalance balance =
        mapper.readValue(
            getClass().getResource("balance-signature-error.json"), BTCTradeBalance.class);
    assertNotNull(balance.getResult());
    assertEquals("signature error", balance.getMessage());
    assertFalse(balance.isSuccess());
  }

  @Test
  public void testUnmarshalBalance() throws IOException {

    BTCTradeBalance balance =
        mapper.readValue(getClass().getResource("balance.json"), BTCTradeBalance.class);
    assertNull(balance.getResult());
    assertNull(balance.getMessage());
    assertTrue(balance.isSuccess());
    assertEquals(1L, balance.getUid().longValue());
    assertEquals(1, balance.getNameauth().intValue());
    assertEquals("0", balance.getMoflag());
    assertEquals(new BigDecimal("1"), balance.getBtcBalance());
    assertEquals(new BigDecimal("2"), balance.getBtcReserved());
    assertEquals(new BigDecimal("3"), balance.getLtcBalance());
    assertEquals(new BigDecimal("4"), balance.getLtcReserved());
    assertEquals(new BigDecimal("5"), balance.getDogeBalance());
    assertEquals(new BigDecimal("6"), balance.getDogeReserved());
    assertEquals(new BigDecimal("7"), balance.getYbcBalance());
    assertEquals(new BigDecimal("8"), balance.getYbcReserved());
    assertEquals(new BigDecimal("9"), balance.getCnyBalance());
    assertEquals(new BigDecimal("10"), balance.getCnyReserved());
  }
}
