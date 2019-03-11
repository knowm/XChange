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

import org.knowm.xchange.currency.CurrencyPair;

/**
 * Conversion between XChange CurrencyPair and Coinmate API
 *
 * @author Martin Stachon
 */
public class CoinmateUtils {

  public static String getPair(CurrencyPair currencyPair) {
    if (currencyPair == null) {
      return null;
    }
    return currencyPair.base.getCurrencyCode().toUpperCase()
        + "_"
        + currencyPair.counter.getCurrencyCode().toUpperCase();
  }

  public static CurrencyPair getPair(String currencyPair) {
    if ("BTC_EUR".equals(currencyPair)) {
      return CurrencyPair.BTC_EUR;
    } else if ("BTC_CZK".equals(currencyPair)) {
      return CurrencyPair.BTC_CZK;
    } else if ("LTC_BTC".equals(currencyPair)) {
      return CurrencyPair.LTC_BTC;
    } else if ("BCH_BTC".equals(currencyPair)) {
      return CurrencyPair.BCH_BTC;
    } else {
      return null;
    }
  }
}
