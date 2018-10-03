/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author Martin Stachon */
public class TickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        TickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/marketdata/example-ticker.json");

    ObjectMapper mapper = new ObjectMapper();
    CoinmateTicker coinmateTicker = mapper.readValue(is, CoinmateTicker.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(coinmateTicker.getData().getLast()).isEqualTo(new BigDecimal("254.08"));
    assertThat(coinmateTicker.getData().getHigh()).isEqualTo(new BigDecimal("255.43"));
    assertThat(coinmateTicker.getData().getLow()).isEqualTo(new BigDecimal("250.02"));
    assertThat(coinmateTicker.getData().getAmount()).isEqualTo(new BigDecimal("42.78294066"));
    assertThat(coinmateTicker.getData().getBid()).isEqualTo(new BigDecimal("252.93"));
    assertThat(coinmateTicker.getData().getAsk()).isEqualTo(new BigDecimal("254.08"));
  }
}
