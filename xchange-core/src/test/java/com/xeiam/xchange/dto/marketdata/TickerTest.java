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
package com.xeiam.xchange.dto.marketdata;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by kpysniak on 12/06/14.
 */
public class TickerTest {

  @Test
  public void testTickerPersistence() {
    TickerBuilder tickerBuilder = TickerBuilder.newInstance();
    CurrencyPair currencyPair = CurrencyPair.BTC_USD;
    BigDecimal last = new BigDecimal(0);
    BigDecimal bid = new BigDecimal(0);
    BigDecimal ask = new BigDecimal(0);
    BigDecimal high = new BigDecimal(0);
    BigDecimal low = new BigDecimal(0);
    BigDecimal volume = new BigDecimal(0);
    Date timestamp = new Date();

    BigDecimal average = new BigDecimal(0);
    BigDecimal otherAttribute = new BigDecimal(0);

    Ticker ticker = tickerBuilder.withCurrencyPair(currencyPair).withLast(last).withBid(bid)
      .withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp)
      .withAttribute("average", average).withAttribute("otherAttribute", otherAttribute).build();

    assertThat(ticker.getCurrencyPair()).isSameAs(currencyPair);
    assertThat(ticker.getLast()).isSameAs(last);
    assertThat(ticker.getAsk()).isSameAs(ask);
    assertThat(ticker.getBid()).isSameAs(bid);
    assertThat(ticker.getHigh()).isSameAs(high);
    assertThat(ticker.getLow()).isSameAs(low);
    assertThat(ticker.getVolume()).isSameAs(volume);
    assertThat(ticker.getTimestamp()).isSameAs(timestamp);

    assertThat(ticker.getAttribute("currencyPair")).isSameAs(currencyPair);
    assertThat(ticker.getAttribute("last")).isSameAs(last);
    assertThat(ticker.getAttribute("ask")).isSameAs(ask);
    assertThat(ticker.getAttribute("bid")).isSameAs(bid);
    assertThat(ticker.getAttribute("high")).isSameAs(high);
    assertThat(ticker.getAttribute("low")).isSameAs(low);
    assertThat(ticker.getAttribute("volume")).isSameAs(volume);
    assertThat(ticker.getAttribute("timestamp")).isSameAs(timestamp);
    assertThat(ticker.getAttribute("average")).isSameAs(average);
    assertThat(ticker.getAttribute("otherAttribute")).isSameAs(otherAttribute);
  }

}
