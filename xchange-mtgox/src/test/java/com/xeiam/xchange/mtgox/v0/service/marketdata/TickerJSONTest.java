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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.mtgox.v0.dto.marketdata.MtGoxTicker;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Test MtGoxTicker JSON parsing
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data-v0.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat("Unexpected Return Buy value", mtGoxTicker.getTicker().getBuy().doubleValue(), equalTo(16.79036));
    assertThat("Unexpected Return Last value", mtGoxTicker.getTicker().getLast().doubleValue(), equalTo(16.800000000000001));
    assertThat("Unexpected Return Ask value", mtGoxTicker.getTicker().getSell().doubleValue(), equalTo(16.800000000000001));
    assertThat("Unexpected Return High value", mtGoxTicker.getTicker().getHigh().doubleValue(), equalTo(16.98));
    assertThat("Unexpected Return Low value", mtGoxTicker.getTicker().getLow().doubleValue(), equalTo(15.634119999999999));
    assertThat("Unexpected Return Volume value", mtGoxTicker.getTicker().getVol().longValue(), equalTo(60418L));

  }
}
