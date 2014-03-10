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
package com.xeiam.xchange.anx;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;



/**
 * A central place for shared Mt Gox properties
 */
public final class ANXUtils {

  /**
   * private Constructor
   */
  private ANXUtils() {

  }

  /**
   * <p>
   * According to Mt.Gox API docs (https://en.bitcoin.it/wiki/ANX/API), data is cached for 10 seconds.
   * </p>
   */
  public static final int REFRESH_RATE = 10; // [seconds]

  public static final int BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR = 100000000;

  public static final int PRICE_INT_2_DECIMAL_FACTOR = 100000;

  public static final int JPY_SEK_PRICE_INT_2_DECIMAL_FACTOR = 1000;

  /**
   * Converts an amount to a properly scaled int-String for Mt Gox
   * 
   * @param amount
   * @return
   */
  public static String getAmountString(BigDecimal amount) {

    return amount.multiply(new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)).toPlainString();
  }

  /**
   * Converts a price in decimal form to a properly scaled int-String for Mt Gox
   * 
   * @param price
   * @return
   */
//  public static String getPriceString(BigMoney price) {
//
//    if (!(price.getCurrencyUnit().toString().equals("JPY") || price.getCurrencyUnit().toString().equals("SEK"))) {
//      return price.getAmount().multiply(new BigDecimal(ANXUtils.PRICE_INT_2_DECIMAL_FACTOR)).stripTrailingZeros().toPlainString();
//    }
//    else { // JPY, SEK
//      return price.getAmount().multiply(new BigDecimal(ANXUtils.JPY_SEK_PRICE_INT_2_DECIMAL_FACTOR)).stripTrailingZeros().toPlainString();
//    }
//  }

  /**
   * Converts a currency and long price into a BigMoney Object
   * 
   * @param currency
   * @param price
   * @return
   */
//  public static BigMoney getPrice(String currency, long price) {
//
//    if (!(currency.equals("JPY") || currency.equals("SEK"))) {
//      return MoneyUtils.parse(currency + " " + new BigDecimal(price).divide(new BigDecimal(ANXUtils.PRICE_INT_2_DECIMAL_FACTOR)));
//    }
//    else { // JPY
//      return MoneyUtils.parse(currency + " " + new BigDecimal(price).divide(new BigDecimal(ANXUtils.JPY_SEK_PRICE_INT_2_DECIMAL_FACTOR)));
//    }
//  }

  public static BigDecimal getPrice(long price) {
      return new BigDecimal(price).divide(new BigDecimal(ANXUtils.PRICE_INT_2_DECIMAL_FACTOR));
  }

  public static String urlEncode(String str) {

    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Problem encoding, probably bug in code.", e);
    }
  }

  public static long getNonce() {

    return System.currentTimeMillis();
  }
}
