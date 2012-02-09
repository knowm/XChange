/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.imcex.v1;

import com.xeiam.xchange.imcex.v1.service.marketdata.dto.ImcexTicker;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ImcexTickerTest {

  // TODO Map the JSON objects for the Imcex exchange
  @Ignore
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = ImcexTickerTest.class.getResourceAsStream("/imcex/example-trades-btc-lrused.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ImcexTicker imcexTicker = mapper.readValue(is, ImcexTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat("Unexpected Return Buy value",imcexTicker.getReturn().getBuy().getValue(),equalTo("5.77397"));
  }
}
