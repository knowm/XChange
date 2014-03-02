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

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;

/**
 * @author jamespedwards42
 */
public class JustcoinTickerTest {

  private String tradableIdentifier;
  private String currency;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal last;
  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal volume;
  private JustcoinTicker justcoinTicker;

  @Before
  public void before() {

    // initialize expected values
    tradableIdentifier = Currencies.BTC;
    currency = Currencies.XRP;
    high = BigDecimal.valueOf(43998.000);
    low = BigDecimal.valueOf(40782.944);
    last = BigDecimal.valueOf(40900.000);
    bid = BigDecimal.valueOf(40905.000);
    ask = BigDecimal.valueOf(43239.000);
    volume = BigDecimal.valueOf(26.39377);
    justcoinTicker = new JustcoinTicker(JustcoinUtils.getApiMarket(tradableIdentifier, currency), high, low, volume, last, bid, ask, 3);
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinTickerTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final JustcoinTicker[] tickers = mapper.readValue(is, new JustcoinTicker[0].getClass());

    // Verify that the example data was unmarshalled correctly
    assertThat(tickers.length).isEqualTo(5);
    final JustcoinTicker xrpTicker = tickers[4];
    assertThat(xrpTicker).isEqualTo(justcoinTicker);
  }

  @Test
  public void testAdapter() {

    final Ticker ticker = JustcoinAdapters.adaptTicker(new JustcoinTicker[] { justcoinTicker }, new CurrencyPair(tradableIdentifier, currency));

    assertThat(ticker.getLast()).isEqualTo(last);
    assertThat(ticker.getHigh()).isEqualTo(high);
    assertThat(ticker.getLow()).isEqualTo(low);
    assertThat(ticker.getBid()).isEqualTo(bid);
    assertThat(ticker.getAsk()).isEqualTo(ask);
    assertThat(ticker.getVolume()).isEqualTo(volume);
    assertThat(ticker.getTimestamp()).isNull();
    assertThat(ticker.getCurrencyPair().baseCurrency).isEqualTo(tradableIdentifier);
  }
}
