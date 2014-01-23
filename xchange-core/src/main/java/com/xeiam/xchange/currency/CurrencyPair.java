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
 * THE SOFTWARE IS PROVIDED "AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.currency;

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
  public static final CurrencyPair EUR_USD = new CurrencyPair(Currencies.EUR);
  public static final CurrencyPair GBP_USD = new CurrencyPair(Currencies.GBP);
  public static final CurrencyPair USD_JPY = new CurrencyPair(Currencies.USD, Currencies.JPY);
  public static final CurrencyPair USD_CHF = new CurrencyPair(Currencies.USD, Currencies.CHF);
  public static final CurrencyPair USD_AUD = new CurrencyPair(Currencies.USD, Currencies.AUD);
  public static final CurrencyPair USD_CAD = new CurrencyPair(Currencies.USD, Currencies.CAD);

  // Provide some courtesy BTC major symbols
  public static final CurrencyPair BTC_USD = new CurrencyPair(Currencies.BTC);
  public static final CurrencyPair BTC_GBP = new CurrencyPair(Currencies.BTC, Currencies.GBP);
  public static final CurrencyPair BTC_EUR = new CurrencyPair(Currencies.BTC, Currencies.EUR);
  public static final CurrencyPair BTC_JPY = new CurrencyPair(Currencies.BTC, Currencies.JPY);
  public static final CurrencyPair BTC_CHF = new CurrencyPair(Currencies.BTC, Currencies.CHF);
  public static final CurrencyPair BTC_AUD = new CurrencyPair(Currencies.BTC, Currencies.AUD);
  public static final CurrencyPair BTC_CAD = new CurrencyPair(Currencies.BTC, Currencies.CAD);
  public static final CurrencyPair BTC_CNY = new CurrencyPair(Currencies.BTC, Currencies.CNY);
  public static final CurrencyPair BTC_DKK = new CurrencyPair(Currencies.BTC, Currencies.DKK);
  public static final CurrencyPair BTC_HKD = new CurrencyPair(Currencies.BTC, Currencies.HKD);
  public static final CurrencyPair BTC_NZD = new CurrencyPair(Currencies.BTC, Currencies.NZD);
  public static final CurrencyPair BTC_PLN = new CurrencyPair(Currencies.BTC, Currencies.PLN);
  public static final CurrencyPair BTC_RUB = new CurrencyPair(Currencies.BTC, Currencies.RUB);
  public static final CurrencyPair BTC_SEK = new CurrencyPair(Currencies.BTC, Currencies.SEK);
  public static final CurrencyPair BTC_SGD = new CurrencyPair(Currencies.BTC, Currencies.SGD);
  public static final CurrencyPair BTC_NOK = new CurrencyPair(Currencies.BTC, Currencies.NOK);
  public static final CurrencyPair BTC_THB = new CurrencyPair(Currencies.BTC, Currencies.THB);

  public static final CurrencyPair BTC_RUR = new CurrencyPair(Currencies.BTC, Currencies.RUR);
  public static final CurrencyPair LTC_BTC = new CurrencyPair(Currencies.LTC, Currencies.BTC);
  public static final CurrencyPair LTC_USD = new CurrencyPair(Currencies.LTC, Currencies.USD);
  public static final CurrencyPair LTC_RUR = new CurrencyPair(Currencies.LTC, Currencies.RUR);
  public static final CurrencyPair LTC_EUR = new CurrencyPair(Currencies.LTC, Currencies.EUR);
  public static final CurrencyPair NMC_BTC = new CurrencyPair(Currencies.NMC, Currencies.BTC);
  public static final CurrencyPair NMC_USD = new CurrencyPair(Currencies.NMC, Currencies.USD);
  public static final CurrencyPair USD_RUR = new CurrencyPair(Currencies.USD, Currencies.RUR);

  public static final CurrencyPair NVC_BTC = new CurrencyPair(Currencies.NVC, Currencies.BTC);
  public static final CurrencyPair NVC_USD = new CurrencyPair(Currencies.NVC, Currencies.USD);
  public static final CurrencyPair TRC_BTC = new CurrencyPair(Currencies.TRC, Currencies.BTC);
  public static final CurrencyPair PPC_BTC = new CurrencyPair(Currencies.PPC, Currencies.BTC);
  public static final CurrencyPair PPC_USD = new CurrencyPair(Currencies.PPC, Currencies.USD);
  public static final CurrencyPair FTC_BTC = new CurrencyPair(Currencies.FTC, Currencies.BTC);
  public static final CurrencyPair XPM_BTC = new CurrencyPair(Currencies.XPM, Currencies.BTC);

  public static final CurrencyPair BTC_ZAR = new CurrencyPair(Currencies.BTC, Currencies.ZAR);
  public static final CurrencyPair BTC_BRL = new CurrencyPair(Currencies.BTC, Currencies.BRL);
  public static final CurrencyPair BTC_CZK = new CurrencyPair(Currencies.BTC, Currencies.CZK);
  public static final CurrencyPair BTC_ILS = new CurrencyPair(Currencies.BTC, Currencies.ILS);

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

    this(baseCurrency, Currencies.USD);
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
    }
    else if (!baseCurrency.equals(other.baseCurrency)) {
      return false;
    }
    if (counterCurrency == null) {
      if (other.counterCurrency != null) {
        return false;
      }
    }
    else if (!counterCurrency.equals(other.counterCurrency)) {
      return false;
    }
    return true;
  }

}
