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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CurrencyPairTest {

  @Test
  public void testMajors() {

    assertEquals("EUR", CurrencyPair.EUR_USD.baseCurrency);
    assertEquals("USD", CurrencyPair.EUR_USD.counterCurrency);

    assertEquals("GBP", CurrencyPair.GBP_USD.baseCurrency);
    assertEquals("USD", CurrencyPair.GBP_USD.counterCurrency);

    assertEquals("USD", CurrencyPair.USD_JPY.baseCurrency);
    assertEquals("JPY", CurrencyPair.USD_JPY.counterCurrency);

    assertEquals("USD", CurrencyPair.USD_CHF.baseCurrency);
    assertEquals("CHF", CurrencyPair.USD_CHF.counterCurrency);

    assertEquals("USD", CurrencyPair.USD_AUD.baseCurrency);
    assertEquals("AUD", CurrencyPair.USD_AUD.counterCurrency);

    assertEquals("USD", CurrencyPair.USD_CAD.baseCurrency);
    assertEquals("CAD", CurrencyPair.USD_CAD.counterCurrency);
  }

  @Test
  public void testBitcoinCourtesy() {

    assertEquals("BTC", CurrencyPair.BTC_USD.baseCurrency);
    assertEquals("USD", CurrencyPair.BTC_USD.counterCurrency);

    assertEquals("BTC", CurrencyPair.BTC_GBP.baseCurrency);
    assertEquals("USD", CurrencyPair.BTC_USD.counterCurrency);

    assertEquals("BTC", CurrencyPair.BTC_EUR.baseCurrency);
    assertEquals("EUR", CurrencyPair.BTC_EUR.counterCurrency);

    assertEquals("BTC", CurrencyPair.BTC_JPY.baseCurrency);
    assertEquals("JPY", CurrencyPair.BTC_JPY.counterCurrency);

    assertEquals("BTC", CurrencyPair.BTC_CHF.baseCurrency);
    assertEquals("CHF", CurrencyPair.BTC_CHF.counterCurrency);

    assertEquals("BTC", CurrencyPair.BTC_AUD.baseCurrency);
    assertEquals("AUD", CurrencyPair.BTC_AUD.counterCurrency);

    assertEquals("BTC", CurrencyPair.BTC_CAD.baseCurrency);
    assertEquals("CAD", CurrencyPair.BTC_CAD.counterCurrency);

  }

}
