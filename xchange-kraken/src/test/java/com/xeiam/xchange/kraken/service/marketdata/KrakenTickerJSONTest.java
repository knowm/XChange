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
import java.math.BigDecimal;

import org.junit.Test;
import org.xchange.kraken.dto.marketdata.KrakenTickerResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test KrakenTicker JSON parsing
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
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getAsk()[0]).isEqualTo(new BigDecimal("96.99000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getAsk()[1]).isEqualTo(new BigDecimal("1"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getBid()[0]).isEqualTo(new BigDecimal("96.00000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getBid()[1]).isEqualTo(new BigDecimal("1"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getClose()[0]).isEqualTo(new BigDecimal("96.75000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getClose()[1]).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getVolume()[0]).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getVolume()[1]).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getVolumeAvg()[0]).isEqualTo(new BigDecimal("0.00000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getVolumeAvg()[1]).isEqualTo(new BigDecimal("0.00000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getTrades()[0]).isEqualTo(new BigDecimal("0"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getTrades()[1]).isEqualTo(new BigDecimal("0"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getLow()[0]).isEqualTo(new BigDecimal("96.75000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getLow()[1]).isEqualTo(new BigDecimal("0.00000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getHigh()[0]).isEqualTo(new BigDecimal("96.75000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getHigh()[1]).isEqualTo(new BigDecimal("0.00000"));
    assertThat(krakenTicker.getResult().get("XBTCZEUR").getOpen()).isEqualTo(new BigDecimal("96.75000"));
  }
}
