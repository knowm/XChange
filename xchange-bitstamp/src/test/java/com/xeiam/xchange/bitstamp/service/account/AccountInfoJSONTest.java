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
package com.xeiam.xchange.bitstamp.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;

/**
 * Test BitStamp Full Depth JSON parsing
 */
public class AccountInfoJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = AccountInfoJSONTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampBalance bitstampBalance = mapper.readValue(is, BitstampBalance.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitstampBalance.getBtcAvailable()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(bitstampBalance.getBtcReserved()).isEqualTo(new BigDecimal("0"));
    assertThat(bitstampBalance.getBtcBalance()).isEqualTo(new BigDecimal("6.99990000"));
    assertThat(bitstampBalance.getUsdAvailable()).isEqualTo(new BigDecimal("0.00"));
    assertThat(bitstampBalance.getUsdBalance()).isEqualTo(new BigDecimal("172.87"));
    assertThat(bitstampBalance.getUsdReserved()).isEqualTo(new BigDecimal("172.87"));
    assertThat(bitstampBalance.getFee()).isEqualTo(new BigDecimal("0.5000"));
  }
}
