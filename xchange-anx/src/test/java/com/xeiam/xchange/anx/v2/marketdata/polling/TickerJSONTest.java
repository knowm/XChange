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
package com.xeiam.xchange.anx.v2.marketdata.polling;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTicker;

/**
 * Test ANXTicker JSON parsing
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/v2/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ANXTicker anxTicker = mapper.readValue(is, ANXTicker.class);
    //
    System.out.println(anxTicker.toString());

    // Verify that the example data was unmarshalled correctly
    Assert.assertEquals(new BigDecimal("725.38123"), anxTicker.getHigh().getValue());
    Assert.assertEquals(new BigDecimal("380.00000"), anxTicker.getLow().getValue());
    Assert.assertEquals(new BigDecimal("429.34018"), anxTicker.getAvg().getValue());
    Assert.assertEquals(new BigDecimal("429.34018"), anxTicker.getVwap().getValue());
    Assert.assertEquals(new BigDecimal("7.00000000"), anxTicker.getVol().getValue());
    Assert.assertEquals(new BigDecimal("725.38123"), anxTicker.getLast().getValue());
    Assert.assertEquals(new BigDecimal("38.85148"), anxTicker.getBuy().getValue());
    Assert.assertEquals(new BigDecimal("897.25596"), anxTicker.getSell().getValue());
    Assert.assertEquals(1393388594814000L, anxTicker.getNow());

  }
}
