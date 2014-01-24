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
package com.xeiam.xchange.justcoin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.Base64;

/**
 * A central place for shared Justcoin properties
 * 
 * jamespedwards42
 */
public final class JustcoinUtils {

  /**
   * private Constructor
   */
  private JustcoinUtils() {

  }

  public static final Set<String> CURRENCIES = new HashSet<String>(Arrays.asList("USD", "EUR", "NOK", "BTC", "LTC", "XRP"));

  public static final Set<CurrencyPair> CURRENCY_PAIRS = new HashSet<CurrencyPair>(Arrays.asList(

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_LTC,

  CurrencyPair.BTC_EUR,

  CurrencyPair.BTC_NOK,

  CurrencyPair.BTC_XRP));

  /**
   * Checks if a given CurrencyPair is covered by this exchange
   * 
   * @param currencyPair
   * @return
   */
  public static boolean isValidCurrencyPair(final CurrencyPair currencyPair) {

    return CURRENCY_PAIRS.contains(currencyPair);
  }

  public static String getApiCurrencyPair(final String tradableIdentifier, final String currency) {

    return tradableIdentifier + currency;
  }

  public static String getBasicAuth(final String user, final String pass) {

    return "Basic " + Base64.encodeBytes((user + ":" + pass).getBytes());
  }
}
