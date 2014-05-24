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
package com.xeiam.xchange.virtex.v2.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTicker;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTickerWrapper;

/**
 * Test VirtExTicker JSON parsing
 */
public class VirtExTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTickerWrapper virtExTickerWrapper = mapper.readValue(is, VirtExTickerWrapper.class);
    VirtExTicker virtExTicker = virtExTickerWrapper.getTicker(); 

    // Verify that the example data was unmarshalled correctly
    assertThat(virtExTicker.getLast()).isEqualTo(new BigDecimal("545.060000000"));
    assertThat(virtExTicker.getHigh()).isEqualTo(new BigDecimal("574.000000000"));
    assertThat(virtExTicker.getLow()).isEqualTo(new BigDecimal("538.000000000"));
    assertThat(virtExTicker.getVolume()).isEqualTo(new BigDecimal("284.231600000"));
    assertThat(virtExTicker.getSell()).isEqualTo(new BigDecimal("545.06000"));
    assertThat(virtExTicker.getBuy()).isEqualTo(new BigDecimal("545.03000"));
    
  }

}
