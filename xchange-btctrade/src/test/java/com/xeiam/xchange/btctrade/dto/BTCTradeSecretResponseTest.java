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
package com.xeiam.xchange.btctrade.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCTradeSecretResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testError() throws IOException {

    BTCTradeSecretResponse response = mapper.readValue(getClass().getResource("secret-error.json"), BTCTradeSecretResponse.class);
    assertNotNull(response.getResult());
    assertEquals("parameter error", response.getMessage());
    assertNull(response.getData());
    assertFalse(response.isSuccess());
  }

  @Test
  public void testReadonly() throws IOException {

    BTCTradeSecretResponse response = mapper.readValue(getClass().getResource("secret-readonly.json"), BTCTradeSecretResponse.class);
    assertNotNull(response.getResult());
    assertTrue(response.isSuccess());
    assertEquals("020f4c12f43740c46bac1265d9145634c01b63c0", response.getData().getSecret());
    assertEquals("1", response.getData().getId());
    assertEquals("1", response.getData().getUid());
    assertEquals("readonly", response.getData().getLevel());
    assertEquals("2014-06-23 03:12:18", response.getData().getExpires());
  }

  @Test
  public void testFull() throws IOException {

    BTCTradeSecretResponse response = mapper.readValue(getClass().getResource("secret-full.json"), BTCTradeSecretResponse.class);
    assertTrue(response.getResult());
    assertEquals("04819314e59950d2b6c41faedd1291162739e8b8", response.getData().getSecret());
    assertEquals("2", response.getData().getId());
    assertEquals("1", response.getData().getUid());
    assertEquals("full", response.getData().getLevel());
    assertEquals("2014-06-23 03:30:35", response.getData().getExpires());
  }

}
