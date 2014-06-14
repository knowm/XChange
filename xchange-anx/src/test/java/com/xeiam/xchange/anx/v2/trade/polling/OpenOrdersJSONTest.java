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
package com.xeiam.xchange.anx.v2.trade.polling;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrder;

/**
 * Test ANXOpenOrders JSON parsing
 */
public class OpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = OpenOrdersJSONTest.class.getResourceAsStream("/v2/trade/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ANXOpenOrder[] anxOpenOrders = mapper.readValue(is, ANXOpenOrder[].class);

    // System.out.println(new Date(anxOpenOrders[0].getDate()));

    // Verify that the example data was unmarshalled correctly
    Assert.assertEquals("7eecf4b2-5785-4500-a5d4-f3f8c924395c", anxOpenOrders[1].getOid());
    Assert.assertEquals("BTC", anxOpenOrders[1].getItem());
    Assert.assertEquals("HKD", anxOpenOrders[1].getCurrency());
    Assert.assertEquals("bid", anxOpenOrders[1].getType());
    Assert.assertEquals("BTC", anxOpenOrders[1].getAmount().getCurrency());
    Assert.assertEquals(new BigDecimal("10.00000000"), anxOpenOrders[1].getAmount().getValue());

    Assert.assertEquals(new BigDecimal("412.34567"), anxOpenOrders[0].getPrice().getValue());
    Assert.assertEquals(new BigDecimal("212.34567"), anxOpenOrders[1].getPrice().getValue());
    Assert.assertEquals("open", anxOpenOrders[1].getStatus());
  }
}
