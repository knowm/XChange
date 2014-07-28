/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.btctrade.dto.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCTradeBalanceTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testError() throws IOException {

    BTCTradeBalance balance = mapper.readValue(getClass().getResource("balance-signature-error.json"), BTCTradeBalance.class);
    assertNotNull(balance.getResult());
    assertEquals("signature error", balance.getMessage());
    assertFalse(balance.isSuccess());
  }

  @Test
  public void testUnmarshalBalance() throws IOException {

    BTCTradeBalance balance = mapper.readValue(getClass().getResource("balance.json"), BTCTradeBalance.class);
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
