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
package com.xeiam.xchange.btcchina.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrders;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;

/**
 * Test Transaction[] JSON parsing
 */
public class PlaceLimitOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = PlaceLimitOrderJSONTest.class.getResourceAsStream("/trade/example-place-limit-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCChinaGetOrdersResponse btcChinaResponse = mapper.readValue(is, BTCChinaGetOrdersResponse.class);
    System.out.println(btcChinaResponse);

    BTCChinaOrders btcChinaOrders = btcChinaResponse.getResult();
    System.out.println(btcChinaOrders.toString());

    assertThat(btcChinaOrders.getOrders().get(0).getId()).isEqualTo(4972937);
    assertThat(btcChinaOrders.getOrders().get(0).getCurrency()).isEqualTo("CNY");

  }
}
