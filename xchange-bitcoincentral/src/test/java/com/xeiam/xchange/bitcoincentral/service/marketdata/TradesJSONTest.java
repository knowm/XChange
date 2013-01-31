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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTrade;

/**
 * Test Transaction[] JSON parsing
 */
public class TradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoinCentralTrade[] bitcoinCentralTrades = mapper.readValue(is, BitcoinCentralTrade[].class);

    // Verify that the example data was unmarshalled correctly
    // assertThat(bitcoinCentralTrades[0].getCreatedAt(), is(equalTo("2011-07-05T00:12:07+02:00")));
    assertThat(bitcoinCentralTrades[0].getCurrency(), is(equalTo("EUR")));
    assertThat(bitcoinCentralTrades[0].getPpc(), is(equalTo(new BigDecimal("11.1"))));
    assertThat(bitcoinCentralTrades[0].getTradedBtc(), is(equalTo(new BigDecimal("1.0"))));

    // Trades trades = BitcoinCentralAdapters.adaptTrades(bitcoinCentralTrades, "EUR", "BTC");
    // assertThat(trades.getTrades().get(0).getTimestamp().getTime(), is(equalTo(1309817527000L)));

  }
}
