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
package com.xeiam.xchange.kraken.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.xchange.kraken.dto.marketdata.KrakenTradesResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test KrakenDepth JSON parsing
 */
public class KrakenTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradesResult krakenDepth = mapper.readValue(is, KrakenTradesResult.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(krakenDepth.getResult().getTradesPerCurrencyPair("XBTCZEUR")).isNull();
    assertThat(krakenDepth.getResult().getTradesPerCurrencyPair("XXBTZEUR")[0][0]).isEqualTo("92.50000");
    assertThat(krakenDepth.getResult().getTradesPerCurrencyPair("XXBTZEUR")[0][4]).isEqualTo("l");
    assertThat(krakenDepth.getResult().getTradesPerCurrencyPair("XXBTZEUR")[1][2]).isEqualTo("1380018049.8094");
    Long date = krakenDepth.getResult().getLast();
    assertThat(date).isEqualTo(1380018049809381679L);
  }
}
