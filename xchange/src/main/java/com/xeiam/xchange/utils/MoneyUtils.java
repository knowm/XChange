/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

/**
 * <p>
 * Utilities to provide the following to application:
 * </p>
 * <ul>
 * <li>Various shortcuts to common currencies from Joda Money</li>
 * </ul>
 */
public class MoneyUtils {

  /**
   * @param value A general-purpose currency and value representation (e.g. "USD 3210.12345678")
   * @return A standard fiat currency BigMoney that can handle complex calculations and display using a scale inferred from the minor part
   * @see org.joda.money.Money For a simpler approach for fiat currencies not requiring precise calculations (e.g. display only)
   */
  public static BigMoney parseFiat(String value) {
    try {
      return BigMoney.parse(value);
    } catch (IllegalArgumentException e) { // for example, BigMoney cannot handle scientific notation in its constructor
      BigDecimal bigDecimal = new BigDecimal(value.substring(4));
      return BigMoney.parse(value.substring(0, 3) + " " + bigDecimal.toPlainString()); // here we attempt to parse it manually with the BigDecimal constructor
    }
  }

  /**
   * @param value A general-purpose currency and value representation (e.g. "BTC 3210.1234567800")
   * @return A standard Bitcoin currency BigMoney that can handle complex calculations using a scale of 12 regardless of the minor part
   */
  public static BigMoney parseBitcoin(String value) {

    BigMoney unscaled = BigMoney.parse(value);
    if (!unscaled.getCurrencyUnit().getCode().equals("BTC")) {
      throw new IllegalArgumentException("Must have BTC currency code");
    }
    return unscaled.withScale(12);
  }

  /**
   * @param value A whole number of satoshis (e.g. 1 provides the same as "BTC 0.00000001")
   * @return A standard Bitcoin currency BigMoney that can handle complex calculations using a scale of 12 regardless of the minor part TODO Add method to include a scaling factor from a long to act as a multiplier/divisor
   */
  public static BigMoney fromSatoshi(long value) {

    BigMoney unscaled = BigMoney.ofScale(CurrencyUnit.getInstance("BTC"), value, 8);
    if (!unscaled.getCurrencyUnit().getCode().equals("BTC")) {
      throw new IllegalArgumentException("Must have BTC currency code");
    }
    return unscaled.withScale(12);
  }

  public static String formatBitcoin(BigMoney value) {

    if (!value.getCurrencyUnit().getCode().equals("BTC")) {
      throw new IllegalArgumentException("Must have BTC currency code");
    }
    return value.withScale(8, RoundingMode.HALF_UP).toString();
  }

}
