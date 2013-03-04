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
package com.xeiam.xchange.bitcoin24.service.marketdata;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoin24.dto.marketdata.Bitcoin24Ticker;

/**
 * Test Bitcoin24Ticker JSON parsing
 */
public class Bitcoin24TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = Bitcoin24TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Bitcoin24Ticker Bitcoin24Ticker = mapper.readValue(is, Bitcoin24Ticker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat("Unexpected Return Last value", Bitcoin24Ticker.getLast(), equalTo(new BigDecimal("30.50001")));
    assertThat("Unexpected Return High value", Bitcoin24Ticker.getHigh(), equalTo(new BigDecimal("34.39799")));
    assertThat("Unexpected Return Low value", Bitcoin24Ticker.getLow(), equalTo(new BigDecimal("30.00043")));
    assertThat("Unexpected Return Volume value", Bitcoin24Ticker.getVol(), equalTo(new BigDecimal("12")));
  }

}
