/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.poloniex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author Zach Holmes
 */

public class PoloniexUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    String pairString = currencyPair.counterSymbol.toUpperCase() + "_" + currencyPair.baseSymbol.toUpperCase();
    return pairString;
  }

  public static CurrencyPair toCurrencyPair(String pair) {

    String[] currencies = pair.split("_");
    return new CurrencyPair(currencies[1], currencies[0]);
  }

  public static Date stringToDate(String dateString) {

    try {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
    } catch (ParseException e) {
      return new Date(0);
    }
  }
}
