/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.btctrade.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCTradeDepthTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testBTCTradeDepth() throws IOException {
    BTCTradeDepth depth = mapper.readValue(
        getClass().getResource("depth.json"), BTCTradeDepth.class);

    assertEquals(50, depth.getAsks().length);
    assertEquals(50, depth.getBids().length);

    assertEquals(new BigDecimal("4045.00000"), depth.getAsks()[0][0]);
    assertEquals(new BigDecimal("1.402"), depth.getAsks()[0][1]);

    assertEquals(new BigDecimal("4044.50000"), depth.getAsks()[1][0]);
    assertEquals(new BigDecimal("0.011"), depth.getAsks()[1][1]);

    assertEquals(new BigDecimal("3756.00000"), depth.getAsks()[49][0]);
    assertEquals(new BigDecimal("0.685"), depth.getAsks()[49][1]);

    assertEquals(new BigDecimal("3730.00000"), depth.getBids()[0][0]);
    assertEquals(new BigDecimal("1.066"), depth.getBids()[0][1]);

    assertEquals(new BigDecimal("3728.01000"), depth.getBids()[1][0]);
    assertEquals(new BigDecimal("3.000"), depth.getBids()[1][1]);

    assertEquals(new BigDecimal("2951.00000"), depth.getBids()[49][0]);
    assertEquals(new BigDecimal("4.000"), depth.getBids()[49][1]);
  }

}
