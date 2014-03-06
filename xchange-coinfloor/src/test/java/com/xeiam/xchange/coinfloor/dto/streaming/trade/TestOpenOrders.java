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
package com.xeiam.xchange.coinfloor.dto.streaming.trade;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author obsessiveOrange
 */
public class TestOpenOrders {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = TestOpenOrders.class.getResourceAsStream("/trade/example-openOrders-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorOpenOrders testObject = mapper.readValue(is, CoinfloorOpenOrders.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(301, testObject.getTag());
    Assert.assertEquals(211118, testObject.getOrders().get(0).getId());
    Assert.assertEquals("BTC", testObject.getOrders().get(0).getBase());
    Assert.assertEquals("GBP", testObject.getOrders().get(0).getCounter());
    Assert.assertEquals(new BigDecimal("1"), testObject.getOrders().get(0).getPrice());
    Assert.assertEquals(new BigDecimal("1"), testObject.getOrders().get(0).getBaseQty());
  }
}
