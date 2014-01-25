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
package com.xeiam.xchange.justcoin.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinDepth;

/**
 * @author jamespedwards42
 */
public class JustcoinDepthJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinDepthJsonTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinDepth orderBook = mapper.readValue(is, JustcoinDepth.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getBids().get(0).get(0)).isEqualTo(new BigDecimal("835.000"));
    assertThat(orderBook.getBids().get(0).get(1)).isEqualTo(new BigDecimal("0.05450"));
    assertThat(orderBook.getAsks().get(0).get(0)).isEqualTo(new BigDecimal("844.950"));
    assertThat(orderBook.getAsks().get(0).get(1)).isEqualTo(new BigDecimal("0.02000"));
    assertThat(orderBook.getBids().size()).isEqualTo(43);
    assertThat(orderBook.getAsks().size()).isEqualTo(41);
  }
}
