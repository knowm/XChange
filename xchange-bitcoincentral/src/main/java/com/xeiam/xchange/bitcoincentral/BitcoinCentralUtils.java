/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * Copyright (C) 2013 Matija Mazi
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
package com.xeiam.xchange.bitcoincentral;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared BitcoinCentral properties
 */
public final class BitcoinCentralUtils {

  // private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

  /**
   * private Constructor
   */
  private BitcoinCentralUtils() {

  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_EUR

  // for now, volume too low...
  // CurrencyPair.BTC_GBP,
  // CurrencyPair.BTC_USD

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

  // /**
  // * Format a date String for Bitstamp
  // *
  // * @param dateString
  // * @return
  // */
  // public static Date parseDate(String dateString) {
  //
  // try {
  // return DATE_FORMAT.parse(dateString);
  // } catch (ParseException e) {
  // throw new ExchangeException("Illegal date/time format", e);
  // }
  // }

}
