/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v0.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTicker;

/**
 * Test MtGoxTicker JSON parsing
 * 
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
@Ignore
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/v0/marketdata/example-ticker-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(mtGoxTicker.getTicker().getBuy().doubleValue()).isEqualTo(16.79036);
    assertThat(mtGoxTicker.getTicker().getLast().doubleValue()).isEqualTo(16.800000000000001);
    assertThat(mtGoxTicker.getTicker().getSell().doubleValue()).isEqualTo(16.800000000000001);
    assertThat(mtGoxTicker.getTicker().getHigh().doubleValue()).isEqualTo(16.98);
    assertThat(mtGoxTicker.getTicker().getLow().doubleValue()).isEqualTo(15.634119999999999);
    assertThat(mtGoxTicker.getTicker().getVol().longValue()).isEqualTo(60418L);

  }
}
