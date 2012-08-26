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
package com.xeiam.xchange;

import com.xeiam.xchange.utils.Assert;

/**
 * <p>
 * Value object to provide the following to API:
 * </p>
 * <ul>
 * <li>Provision of major currency symbol pairs (EUR/USD, GBP/USD etc)</li>
 * <li>Provision of arbitrary symbol pairs for exchange index trading, notional currencies etc</li>
 * </ul>
 * <p>
 * Symbol pairs are quoted, for example, as EUR/USD 1.25 such that 1 EUR can be purchased with 1.25 USD
 * </p>
 */
public class CurrencyPair {

  // Provide some standard major symbols
  public static final CurrencyPair EUR_USD = new CurrencyPair("EUR");
  public static final CurrencyPair GBP_USD = new CurrencyPair("GBP");
  public static final CurrencyPair USD_JPY = new CurrencyPair("USD", "JPY");
  public static final CurrencyPair USD_CHF = new CurrencyPair("USD", "CHF");
  public static final CurrencyPair USD_AUD = new CurrencyPair("USD", "AUD");
  public static final CurrencyPair USD_CAD = new CurrencyPair("USD", "CAD");

  // Provide some courtesy BTC major symbols
  public static final CurrencyPair BTC_USD = new CurrencyPair("BTC");
  public static final CurrencyPair BTC_GBP = new CurrencyPair("BTC", "GBP");
  public static final CurrencyPair BTC_EUR = new CurrencyPair("BTC", "EUR");
  public static final CurrencyPair BTC_JPY = new CurrencyPair("BTC", "JPY");
  public static final CurrencyPair BTC_CHF = new CurrencyPair("BTC", "CHF");
  public static final CurrencyPair BTC_AUD = new CurrencyPair("BTC", "AUD");
  public static final CurrencyPair BTC_CAD = new CurrencyPair("BTC", "CAD");

  public final String baseCurrency;
  public final String counterCurrency;

  /**
   * <p>
   * Reduced constructor using the global reserve currency symbol (USD) as the default counter
   * </p>
   * 
   * @param baseCurrency The base symbol (single unit)
   */
  public CurrencyPair(String baseCurrency) {

    this(baseCurrency, "USD");
  }

  /**
   * <p>
   * Full constructor
   * </p>
   * 
   * @param baseCurrency The base symbol (single unit)
   * @param counterCurrency The counter symbol (multiple units)
   */
  public CurrencyPair(String baseCurrency, String counterCurrency) {

    Assert.hasLength(baseCurrency, 3, "baseCurrency cannot be null and must be 3 characters in length");
    Assert.hasLength(counterCurrency, 3, "counterCurrency cannot be null and must be 3 characters in length");
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
  }

  @Override
  public String toString() {

    return baseCurrency + "/" + counterCurrency;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((baseCurrency == null) ? 0 : baseCurrency.hashCode());
    result = prime * result + ((counterCurrency == null) ? 0 : counterCurrency.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CurrencyPair other = (CurrencyPair) obj;
    if (baseCurrency == null) {
      if (other.baseCurrency != null) {
        return false;
      }
    } else if (!baseCurrency.equals(other.baseCurrency)) {
      return false;
    }
    if (counterCurrency == null) {
      if (other.counterCurrency != null) {
        return false;
      }
    } else if (!counterCurrency.equals(other.counterCurrency)) {
      return false;
    }
    return true;
  }

}
