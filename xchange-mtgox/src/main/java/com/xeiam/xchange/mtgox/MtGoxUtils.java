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
package com.xeiam.xchange.mtgox;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.currency.MoneyUtils;

/**
 * A central place for shared Mt Gox properties
 */
public final class MtGoxUtils {

  /**
   * private Constructor
   */
  private MtGoxUtils() {

  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  CurrencyPair.BTC_USD,

  CurrencyPair.BTC_EUR,

  CurrencyPair.BTC_GBP,

  CurrencyPair.BTC_AUD,

  CurrencyPair.BTC_CAD,

  CurrencyPair.BTC_CHF,

  CurrencyPair.BTC_JPY,

  CurrencyPair.BTC_CNY,

  CurrencyPair.BTC_DKK,

  CurrencyPair.BTC_HKD,

  CurrencyPair.BTC_NZD,

  CurrencyPair.BTC_PLN,

  CurrencyPair.BTC_RUB,

  CurrencyPair.BTC_SEK,

  CurrencyPair.BTC_SGD,

  CurrencyPair.BTC_THB

  );

  /**
   * <p>
   * According to Mt.Gox API docs (https://en.bitcoin.it/wiki/MtGox/API), data is cached for 10 seconds.
   * </p>
   */
  public static final int REFRESH_RATE = 10; // [seconds]

  public static final int BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR = 100000000;

  public static final int PRICE_INT_2_DECIMAL_FACTOR = 100000;

  public static final int JPY_PRICE_INT_2_DECIMAL_FACTOR = 1000;

  /**
   * Converts a price in decimal form to a properly scaled int-String for Mt Gox
   * 
   * @param price
   * @return
   */
  public static String getPriceString(BigMoney price) {

    if (!price.getCurrencyUnit().toString().equals("JPY")) {
      return price.getAmount().multiply(new BigDecimal(MtGoxUtils.PRICE_INT_2_DECIMAL_FACTOR)).stripTrailingZeros().toPlainString();
    } else { // JPY
      return price.getAmount().multiply(new BigDecimal(MtGoxUtils.JPY_PRICE_INT_2_DECIMAL_FACTOR)).stripTrailingZeros().toPlainString();
    }
  }

  /**
   * Converts a currency and long price into a BigMoney Object
   * 
   * @param currency
   * @param price
   * @return
   */
  public static BigMoney getPrice(String currency, long price) {

    if (!(currency.equals("JPY") || currency.equals("SEK")) {

      return MoneyUtils.parse(currency + " " + new BigDecimal(price).divide(new BigDecimal(MtGoxUtils.PRICE_INT_2_DECIMAL_FACTOR)));
    } else { // JPY
      return MoneyUtils.parse(currency + " " + new BigDecimal(price).divide(new BigDecimal(MtGoxUtils.JPY_PRICE_INT_2_DECIMAL_FACTOR)));
    }
  }

  /**
   * Checks if a given CurrencyPair is covered by this exchange
   * 
   * @param currencyPair
   * @return
   */
  public static boolean isValidCurrencyPair(CurrencyPair currencyPair) {

    return CURRENCY_PAIRS.contains(currencyPair);
  }

  public static String urlEncode(String str) {

    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Problem encoding, probably bug in code.", e);
    }
  }
}
