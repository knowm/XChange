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
package com.xeiam.xchange.bitcoinaverage.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;

/**
 * Test BitcoinAverageTicker JSON parsing
 */
public class BitcoinAverageAllJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

	    // Read in the JSON from the example resources
	    InputStream is = BitcoinAverageTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-all.json");

	    // Use Jackson to parse it
	    ObjectMapper mapper = new ObjectMapper();
	    BitcoinAverageTickers bitcoinAverageTicker = mapper.readValue(is, BitcoinAverageTickers.class);

	    // Verify that the example data was unmarshalled correctly
	    assertTrue(bitcoinAverageTicker.getTickers().containsKey("USD"));
	    assertThat(bitcoinAverageTicker.getTickers().get("USD").getLast()).isEqualTo("526.54");
	    assertThat(bitcoinAverageTicker.getTickers().get("USD").getAsk()).isEqualTo("527.55");
	    assertThat(bitcoinAverageTicker.getTickers().get("USD").getBid()).isEqualTo("525.62");
	    assertThat(bitcoinAverageTicker.getTickers().get("USD").getVolume()).isEqualTo("91178.27");
	    //assertThat(bitcoinAverageTicker.getTimestamp().toLocaleString()).isEqualTo("16-Apr-2014 6:58:34 PM");  
  }

}
