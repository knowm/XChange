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
package com.xeiam.xchange.anx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * A central place for shared ANX properties
 */
public final class ANXUtils {

  /**
   * private Constructor
   */
  private ANXUtils() {

  }

  public static final int BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR_2 = 100000000;

  public static final int VOLUME_AND_AMOUNT_MAX_SCALE = 8;

  /**
   * Find and match an order with id in orders
   * 
   * @param orders
   * @param order
   * @param id
   * @return
   */
  public static boolean findLimitOrder(List<LimitOrder> orders, LimitOrder order, String id) {

    boolean found = false;

    for (LimitOrder openOrder : orders) {
      if (openOrder.getId().equalsIgnoreCase(id)) {
        if (order.getCurrencyPair().equals(openOrder.getCurrencyPair()) && (order.getTradableAmount().compareTo(openOrder.getTradableAmount()) == 0)
            && (order.getLimitPrice().compareTo(openOrder.getLimitPrice()) == 0)) {
          found = true;
        }
      }
    }

    return found;
  }

  public static int getMaxPriceScale(CurrencyPair currencyPair) {

    if (currencyPair.baseSymbol.equalsIgnoreCase(Currencies.BTC.toString()) || currencyPair.baseSymbol.equalsIgnoreCase(Currencies.LTC.toString())) {
      return 5;
    }
    else {
      return 8;
    }
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
