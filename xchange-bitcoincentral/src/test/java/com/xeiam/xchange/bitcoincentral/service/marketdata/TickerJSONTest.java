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
package com.xeiam.xchange.bitcoincentral.service.marketdata;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTicker;

/**
 * Test BitcoinCentralTicker JSON parsing
 */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinCentralTicker bitcoinCentralTicker = mapper.readValue(is, BitcoinCentralTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat("Unexpected Return Bid value", bitcoinCentralTicker.getBid(), equalTo(new BigDecimal("12.0")));
    assertThat("Unexpected Return Ask value", bitcoinCentralTicker.getAsk(), equalTo(new BigDecimal("12.199999999999999")));
    assertThat("Unexpected Return Last value", bitcoinCentralTicker.getPrice(), equalTo(new BigDecimal("12.25")));
    assertThat("Unexpected Return High value", bitcoinCentralTicker.getHigh(), equalTo(new BigDecimal("12.449999999999999")));
    assertThat("Unexpected Return Low value", bitcoinCentralTicker.getLow(), equalTo(new BigDecimal("11.088800000000001")));
    assertThat("Unexpected Return Volume value", bitcoinCentralTicker.getVolume(), equalTo(new BigDecimal("1038.1701690100001")));
  }

}
