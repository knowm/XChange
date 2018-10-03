/*
 * The MIT License
 *
 * Copyright 2015-2016 Coinmate.
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
package org.knowm.xchange.coinmate;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.junit.Test;
import org.knowm.xchange.coinmate.dto.marketdata.CoinmateTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/** @author Martin Stachon */
public class CoinmateAdapterTest {

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinmateAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/coinmate/dto/marketdata/example-ticker.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinmateTicker bitstampTicker = mapper.readValue(is, CoinmateTicker.class);

    Ticker ticker = CoinmateAdapters.adaptTicker(bitstampTicker, CurrencyPair.BTC_EUR);

    assertThat(ticker.getLast().toString()).isEqualTo("254.08");
    assertThat(ticker.getBid().toString()).isEqualTo("252.93");
    assertThat(ticker.getAsk().toString()).isEqualTo("254.08");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("42.78294066"));
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(ticker.getTimestamp());
    assertThat(dateString).isEqualTo("2017-01-26 20:12:57");
  }
}
