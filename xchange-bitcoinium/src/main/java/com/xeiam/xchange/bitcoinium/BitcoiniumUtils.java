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
package com.xeiam.xchange.bitcoinium;

import java.util.Arrays;
import java.util.List;

/**
 * A central place for shared Bitcoinium properties
 */
public final class BitcoiniumUtils {

  /**
   * private Constructor
   */
  private BitcoiniumUtils() {

  }

  public static final List<String> PRICE_WINDOW = Arrays.asList(

  "2p", "5p", "10p", "20p", "50p", "100p"

  );

  public static final List<String> TIME_WINDOW = Arrays.asList(

  "10m", "1h", "3h", "12h", "24h", "3d", "7d", "30d", "2M"

  );

  /**
   * Creates a valid currency pair for Bitcoinium.com
   * 
   * @param tradableIdentifier
   * @param currency
   * @param exchange
   * @return
   */
  public static String createCurrencyPairString(String tradableIdentifier, String currency) {

    return tradableIdentifier + "_" + currency;

  }

  /**
   * Checks if a given PriceWindow is covered by this exchange
   * 
   * @param priceWindow
   * @return
   */
  public static boolean isValidPriceWindow(String priceWindow) {

    return PRICE_WINDOW.contains(priceWindow);
  }

  /**
   * Checks if a given TimeWindow is covered by this exchange
   * 
   * @param timeWindow
   * @return
   */
  public static boolean isValidTimeWindow(String timeWindow) {

    return TIME_WINDOW.contains(timeWindow);
  }
}
