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
package com.xeiam.xchange.lakebtc.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTicker;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;


public class LakeBTCMarketDataJsonTests {

  @Test
  public void testDeserializeTicker() throws IOException {
  
    // Read in the JSON from the example resources
    InputStream is = LakeBTCMarketDataJsonTests.class.getResourceAsStream("/marketdata/example-ticker-data.json");
    
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    
    LakeBTCTickers tickers = mapper.readValue(is, LakeBTCTickers.class);
    
    LakeBTCTicker cnyTicker = tickers.getCny();
    assertThat(cnyTicker.getAsk()).isEqualTo("3524.07");
    assertThat(cnyTicker.getBid()).isEqualTo("3517.13");
    assertThat(cnyTicker.getLast()).isEqualTo("3524.07");
    assertThat(cnyTicker.getHigh()).isEqualTo("3584.97");
    assertThat(cnyTicker.getLow()).isEqualTo("3480.07");
    assertThat(cnyTicker.getVolume()).isEqualTo("5964.7677");
    
    LakeBTCTicker usdTicker = tickers.getUsd();
    assertThat(usdTicker.getAsk()).isEqualTo("564.63");
    assertThat(usdTicker.getBid()).isEqualTo("564.63");
    assertThat(usdTicker.getLast()).isEqualTo("564.4");
    assertThat(usdTicker.getHigh()).isEqualTo("573.83");
    assertThat(usdTicker.getLow()).isEqualTo("557.7");
    assertThat(usdTicker.getVolume()).isEqualTo("3521.2782");

  }
  
  @Test
  public void testDeserializeOrderBook() throws IOException {
  
    // Read in the JSON from the example resources
    InputStream is = LakeBTCMarketDataJsonTests.class.getResourceAsStream("/marketdata/example-orderbook-data.json");
    
    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    LakeBTCOrderBook orderBook = mapper.readValue(is, LakeBTCOrderBook.class);
    
    BigDecimal[][] asks = orderBook.getAsks();
    assertThat(asks).hasSize(3);
    assertThat(asks[0][0]).isEqualTo("564.87");
    assertThat(asks[0][1]).isEqualTo("22.371");
    
    BigDecimal[][] bids = orderBook.getBids();
    assertThat(bids).hasSize(3);
    assertThat(bids[2][0]).isEqualTo("558.08");
    assertThat(bids[2][1]).isEqualTo("0.9878");
  }
}
