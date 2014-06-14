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
package com.xeiam.xchange;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.currency.CurrencyPair;

public class CurrencyPairTest {

  @Test
  public void testMajors() {

    assertThat(CurrencyPair.EUR_USD.baseSymbol).isEqualTo("EUR");
    assertThat(CurrencyPair.EUR_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.GBP_USD.baseSymbol).isEqualTo("GBP");
    assertThat(CurrencyPair.GBP_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.USD_JPY.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_JPY.counterSymbol).isEqualTo("JPY");

    assertThat(CurrencyPair.USD_CHF.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CHF.counterSymbol).isEqualTo("CHF");

    assertThat(CurrencyPair.USD_AUD.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_AUD.counterSymbol).isEqualTo("AUD");

    assertThat(CurrencyPair.USD_CAD.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CAD.counterSymbol).isEqualTo("CAD");
  }

  @Test
  public void testBitcoinCourtesy() {

    assertThat(CurrencyPair.BTC_USD.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_GBP.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_EUR.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_EUR.counterSymbol).isEqualTo("EUR");

    assertThat(CurrencyPair.BTC_JPY.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_JPY.counterSymbol).isEqualTo("JPY");

    assertThat(CurrencyPair.BTC_CHF.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CHF.counterSymbol).isEqualTo("CHF");

    assertThat(CurrencyPair.BTC_AUD.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_AUD.counterSymbol).isEqualTo("AUD");

    assertThat(CurrencyPair.BTC_CAD.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CAD.counterSymbol).isEqualTo("CAD");

  }

}
