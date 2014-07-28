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
package com.xeiam.xchange.btcchina.dto.trade.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaIcebergOrder;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrder;

public class BTCChinaGetIcebergOrderResponseTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCChinaGetIcebergOrderResponse() throws IOException {

    BTCChinaGetIcebergOrderResponse response = mapper.readValue(getClass().getResource("getIcebergOrder.json"), BTCChinaGetIcebergOrderResponse.class);
    BTCChinaIcebergOrder io = response.getResult().getIcebergOrder();
    assertEquals(1, io.getId());
    assertEquals("bid", io.getType());
    assertEquals(new BigDecimal("40.00"), io.getPrice());
    assertEquals("BTCCNY", io.getMarket());
    assertEquals(new BigDecimal("12.00000000"), io.getAmount());
    assertEquals(new BigDecimal("12.00000000"), io.getAmountOriginal());
    assertEquals(new BigDecimal("5.00000000"), io.getDisclosedAmount());
    assertEquals(new BigDecimal("0.10"), io.getVariance());
    assertEquals(1405412126L, io.getDate());
    assertEquals("open", io.getStatus());
    
    BTCChinaOrder o = io.getOrders()[0];
    assertEquals(3301L, o.getId());
    assertEquals("bid", o.getType());
    assertEquals(new BigDecimal("40.00"), o.getPrice());
    assertEquals("CNY", o.getCurrency());
    assertEquals(new BigDecimal("4.67700000"), o.getAmount());
    assertEquals(new BigDecimal("4.67700000"), o.getAmountOriginal());
    assertEquals(1405412126L, o.getDate());
    assertEquals("open", o.getStatus());
  }

}
