/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcurex.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;

public class BitcurexAccountJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-eur-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcurexFunds.getAddress().equals("1ABcdEfgKEqBPMQ6D2ocuYNJNsXgKPcfV7"));
    assertThat(bitcurexFunds.getBtcs().compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(bitcurexFunds.getEurs().compareTo(new BigDecimal("6160.06838790")) == 0);
  }

  @Test
  public void testUnmarshalPLN() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-pln-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bitcurexFunds.getAddress().equals("1ABcdEfgKEqBPMQ6D2ocuYNJNsXgKPcfV7"));
    assertThat(bitcurexFunds.getBtcs().compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(bitcurexFunds.getPlns().compareTo(new BigDecimal("6160.06838790")) == 0);
  }

}
