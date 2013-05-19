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
package com.xeiam.xchange;

import com.xeiam.xchange.currency.CurrencyPair;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class CurrencyPairTest {

  @Test
  public void testMajors() {

    assertThat(CurrencyPair.EUR_USD.baseCurrency).isEqualTo("EUR");
    assertThat(CurrencyPair.EUR_USD.counterCurrency).isEqualTo("USD");

    assertThat(CurrencyPair.GBP_USD.baseCurrency).isEqualTo("GBP");
    assertThat(CurrencyPair.GBP_USD.counterCurrency).isEqualTo("USD");

    assertThat(CurrencyPair.USD_JPY.baseCurrency).isEqualTo("USD");
    assertThat(CurrencyPair.USD_JPY.counterCurrency).isEqualTo("JPY");

    assertThat(CurrencyPair.USD_CHF.baseCurrency).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CHF.counterCurrency).isEqualTo("CHF");

    assertThat(CurrencyPair.USD_AUD.baseCurrency).isEqualTo("USD");
    assertThat(CurrencyPair.USD_AUD.counterCurrency).isEqualTo("AUD");

    assertThat(CurrencyPair.USD_CAD.baseCurrency).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CAD.counterCurrency).isEqualTo("CAD");
  }

  @Test
  public void testBitcoinCourtesy() {

    assertThat(CurrencyPair.BTC_USD.baseCurrency).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counterCurrency).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_GBP.baseCurrency).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counterCurrency).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_EUR.baseCurrency).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_EUR.counterCurrency).isEqualTo("EUR");

    assertThat(CurrencyPair.BTC_JPY.baseCurrency).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_JPY.counterCurrency).isEqualTo("JPY");

    assertThat(CurrencyPair.BTC_CHF.baseCurrency).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CHF.counterCurrency).isEqualTo("CHF");

    assertThat(CurrencyPair.BTC_AUD.baseCurrency).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_AUD.counterCurrency).isEqualTo("AUD");

    assertThat(CurrencyPair.BTC_CAD.baseCurrency).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CAD.counterCurrency).isEqualTo("CAD");

  }

}
