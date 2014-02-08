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
package com.xeiam.xchange.kraken.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenTickerResult;

/**
 * Test KrakenTicker JSON parsing
 * 
 * @author Raphael Voellmy
 */
public class KrakenTickerJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTickerResult krakenTicker = mapper.readValue(is, KrakenTickerResult.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(krakenTicker.getResult().get("ZEURXLTC")).isEqualTo(null);
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getAsk().getPrice()).isEqualTo(new BigDecimal("562.26651"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getAsk().getVolume()).isEqualTo(new BigDecimal("1"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getBid().getPrice()).isEqualTo(new BigDecimal("560.46600"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getBid().getVolume()).isEqualTo(new BigDecimal("1"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getClose().getPrice()).isEqualTo(new BigDecimal("560.87711"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getClose().getVolume()).isEqualTo(new BigDecimal("0.01447739"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getTodaysVolume()).isEqualTo(new BigDecimal("84.23095922"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").get24HourVolume()).isEqualTo(new BigDecimal("600.91850325"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getTodaysVolumeAvg()).isEqualTo(new BigDecimal("562.19735"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").get24HourVolumeAvg()).isEqualTo(new BigDecimal("576.77284"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getTodaysNumTrades()).isEqualTo(new BigDecimal("305"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").get24HourNumTrades()).isEqualTo(new BigDecimal("2783"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getTodaysLow()).isEqualTo(new BigDecimal("560.00000"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").get24HourLow()).isEqualTo(new BigDecimal("560.00000"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getTodaysHigh()).isEqualTo(new BigDecimal("570.00000"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").get24HourHigh()).isEqualTo(new BigDecimal("591.11000"));
    assertThat(krakenTicker.getResult().get("XXBTZEUR").getOpen()).isEqualTo(new BigDecimal("568.98910"));
  }
}
