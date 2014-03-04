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
package com.xeiam.xchange.coinfloor.dto.streaming.marketdata;

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
public class TestTicker {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = TestTicker.class.getResourceAsStream("/marketdata/example-ticker-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorTicker testObject = mapper.readValue(is, CoinfloorTicker.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(202, testObject.getTag());
    Assert.assertEquals(new BigDecimal("3.2"), testObject.getLast());
    Assert.assertEquals(new BigDecimal(0), testObject.getHigh());
    Assert.assertEquals(new BigDecimal(0), testObject.getLow());
    Assert.assertEquals(new BigDecimal("3.3"), testObject.getAsk());
    Assert.assertEquals(new BigDecimal("3.2"), testObject.getBid());
  }

  @Test
  public void testMapping2() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = TestTicker.class.getResourceAsStream("/marketdata/example-ticker-update.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorTicker testObject = mapper.readValue(is, CoinfloorTicker.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(new BigDecimal(0), testObject.getLast());
    Assert.assertEquals(new BigDecimal(0), testObject.getHigh());
    Assert.assertEquals(new BigDecimal(0), testObject.getLow());
    Assert.assertEquals(new BigDecimal("3.1899"), testObject.getAsk());
    Assert.assertEquals(new BigDecimal(0), testObject.getBid());
  }
}
