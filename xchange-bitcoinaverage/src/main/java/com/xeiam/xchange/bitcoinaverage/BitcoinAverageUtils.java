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
package com.xeiam.xchange.bitcoinaverage;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared BitcoinAverage properties
 */
public final class BitcoinAverageUtils {

  /**
   * private Constructor
   */
  private BitcoinAverageUtils() {

  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_AUD,

  CurrencyPair.BTC_BRL,

  CurrencyPair.BTC_CAD,

  CurrencyPair.BTC_CNY,

  CurrencyPair.BTC_CZK,

  CurrencyPair.BTC_EUR,

  CurrencyPair.BTC_GBP,

  CurrencyPair.BTC_ILS,

  CurrencyPair.BTC_JPY,

  CurrencyPair.BTC_NOK,

  CurrencyPair.BTC_NZD,

  CurrencyPair.BTC_PLN,

  CurrencyPair.BTC_RUB,

  CurrencyPair.BTC_SEK,

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_ZAR

  );

  /**
   * Checks if a given CurrencyPair is covered by this exchange
   * 
   * @param currencyPair
   * @return
   */
  public static boolean isValidCurrencyPair(CurrencyPair currencyPair) {

    return CURRENCY_PAIRS.contains(currencyPair);
  }

}
