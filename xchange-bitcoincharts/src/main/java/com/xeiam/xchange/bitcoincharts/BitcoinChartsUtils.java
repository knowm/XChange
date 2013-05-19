/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the   to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED   WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.bitcoincharts;

import java.util.Arrays;
import java.util.List;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared BitcoinCharts properties
 */
public final class BitcoinChartsUtils {

  /**
   * private Constructor
   */
  private BitcoinChartsUtils() {

  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair("thAUD", Currencies.BTC),

  new CurrencyPair("bitmarketAUD", Currencies.BTC),

  new CurrencyPair("mtgoxAUD", Currencies.BTC),

  new CurrencyPair("ruxumAUD", Currencies.BTC),

  new CurrencyPair("weexAUD", Currencies.BTC),

  new CurrencyPair("wbxAUD", Currencies.BTC),

  new CurrencyPair("cryptoxAUD", Currencies.BTC),

  new CurrencyPair("mrcdBRL", Currencies.BTC),

  new CurrencyPair("bbmBRL", Currencies.BTC),

  new CurrencyPair("mtgoxCAD", Currencies.BTC),

  new CurrencyPair("virtexCAD", Currencies.BTC),

  new CurrencyPair("weexCAD", Currencies.BTC),

  new CurrencyPair("mtgoxCHF", Currencies.BTC),

  new CurrencyPair("ruxumCHF", Currencies.BTC),

  new CurrencyPair("thCLP", Currencies.BTC),

  new CurrencyPair("btcnCNY", Currencies.BTC),

  new CurrencyPair("mtgoxCNY", Currencies.BTC),

  new CurrencyPair("mtgoxDKK", Currencies.BTC),

  new CurrencyPair("bcEUR", Currencies.BTC),

  new CurrencyPair("btcexEUR", Currencies.BTC),

  new CurrencyPair("thEUR", Currencies.BTC),

  new CurrencyPair("rockEUR", Currencies.BTC),

  new CurrencyPair("b7EUR", Currencies.BTC),

  new CurrencyPair("mtgoxEUR", Currencies.BTC),

  new CurrencyPair("aqoinEUR", Currencies.BTC),

  new CurrencyPair("ruxumEUR", Currencies.BTC),

  new CurrencyPair("intrsngEUR", Currencies.BTC),

  new CurrencyPair("btcdeEUR", Currencies.BTC),

  new CurrencyPair("globalEUR", Currencies.BTC),

  new CurrencyPair("vcxEUR", Currencies.BTC),

  new CurrencyPair("imcexEUR", Currencies.BTC),

  new CurrencyPair("btc24EUR", Currencies.BTC),

  new CurrencyPair("bitcurexEUR", Currencies.BTC),

  new CurrencyPair("btceEUR", Currencies.BTC),

  new CurrencyPair("bcmPXGAU", Currencies.BTC),

  new CurrencyPair("bcmBMGAU", Currencies.BTC),

  new CurrencyPair("britcoinGBP", Currencies.BTC),

  new CurrencyPair("bitmarketGBP", Currencies.BTC),

  new CurrencyPair("bcGBP", Currencies.BTC),

  new CurrencyPair("bcGBP", Currencies.BTC),

  new CurrencyPair("intrsngGBP", Currencies.BTC),

  new CurrencyPair("ruxumGBP", Currencies.BTC),

  new CurrencyPair("globalGBP", Currencies.BTC),

  new CurrencyPair("mtgoxHKD", Currencies.BTC),

  new CurrencyPair("ruxumHKD", Currencies.BTC),

  new CurrencyPair("ruxumHUF", Currencies.BTC),

  new CurrencyPair("thINR", Currencies.BTC),

  new CurrencyPair("btcexJPY", Currencies.BTC),

  new CurrencyPair("ruxumJPY", Currencies.BTC),

  new CurrencyPair("mtgoxJPY", Currencies.BTC),

  new CurrencyPair("bitnzNZD", Currencies.BTC),

  new CurrencyPair("mtgoxNZD", Currencies.BTC),

  new CurrencyPair("bitomatPLN", Currencies.BTC),

  new CurrencyPair("bitmarketPLN", Currencies.BTC),

  new CurrencyPair("bitchangePLN", Currencies.BTC),

  new CurrencyPair("mtgoxPLN", Currencies.BTC),

  new CurrencyPair("intrsngPLN", Currencies.BTC),

  new CurrencyPair("ruxumPLN", Currencies.BTC),

  new CurrencyPair("globalPLN", Currencies.BTC),

  new CurrencyPair("freshPLN", Currencies.BTC),

  new CurrencyPair("bitcurexPLN", Currencies.BTC),

  new CurrencyPair("btcexWMR", Currencies.BTC),

  new CurrencyPair("btcexRUB", Currencies.BTC),

  new CurrencyPair("btcexYAD", Currencies.BTC),

  new CurrencyPair("bitmarketRUB", Currencies.BTC),

  new CurrencyPair("mtgoxRUB", Currencies.BTC),

  new CurrencyPair("ruxumRUB", Currencies.BTC),

  new CurrencyPair("btceRUR", Currencies.BTC),

  new CurrencyPair("mtgoxSEK", Currencies.BTC),

  new CurrencyPair("ruxumSEK", Currencies.BTC),

  new CurrencyPair("kptnSEK", Currencies.BTC),

  new CurrencyPair("mtgoxSGD", Currencies.BTC),

  new CurrencyPair("ruxumSGD", Currencies.BTC),

  new CurrencyPair("fybsgSGD", Currencies.BTC),

  new CurrencyPair("virwoxSLL", Currencies.BTC),

  new CurrencyPair("rockSLL", Currencies.BTC),

  new CurrencyPair("ruxumTHB", Currencies.BTC),

  new CurrencyPair("mtgoxTHB", Currencies.BTC),

  new CurrencyPair("ruxumUAH", Currencies.BTC),

  new CurrencyPair("bcmPPUSD", Currencies.BTC),

  new CurrencyPair("mtgoxUSD", Currencies.BTC),

  new CurrencyPair("bcmLRUSD", Currencies.BTC),

  new CurrencyPair("btcexUSD", Currencies.BTC),

  new CurrencyPair("btcexWMZ", Currencies.BTC),

  new CurrencyPair("bcmBMUSD", Currencies.BTC),

  new CurrencyPair("bcmMBUSD", Currencies.BTC),

  new CurrencyPair("b2cUSD", Currencies.BTC),

  new CurrencyPair("thLRUSD", Currencies.BTC),

  new CurrencyPair("thUSD", Currencies.BTC),

  new CurrencyPair("vcxUSD", Currencies.BTC),

  new CurrencyPair("exchbUSD", Currencies.BTC),

  new CurrencyPair("b7USD", Currencies.BTC),

  new CurrencyPair("ruxumUSD", Currencies.BTC),

  new CurrencyPair("cbxUSD", Currencies.BTC),

  new CurrencyPair("btceUSD", Currencies.BTC),

  new CurrencyPair("bitstampUSD", Currencies.BTC),

  new CurrencyPair("globalUSD", Currencies.BTC),

  new CurrencyPair("intrsngUSD", Currencies.BTC),

  new CurrencyPair("bitfloorUSD", Currencies.BTC),

  new CurrencyPair("rockUSD", Currencies.BTC),

  new CurrencyPair("cryptoxUSD", Currencies.BTC),

  new CurrencyPair("icbitUSD", Currencies.BTC),

  new CurrencyPair("bitmeUSD", Currencies.BTC),

  new CurrencyPair("imcexUSD", Currencies.BTC),

  new CurrencyPair("btctreeUSD", Currencies.BTC),

  new CurrencyPair("btc24USD", Currencies.BTC),

  new CurrencyPair("weexUSD", Currencies.BTC),

  new CurrencyPair("ruxumZAR", Currencies.BTC)

  );

  public static final long REFRESH_RATE_MILLIS = 1000L * 60L * 15L; // rates refresh every 15 minutes

  /**
   * Checks if a given CurrencyPair is covered by this exchange
   * 
   * @param currencyPair
   * @return
   */
  public static boolean isValidCurrencyPair(CurrencyPair currencyPair) {

    return CURRENCY_PAIRS.contains(currencyPair);
  }

}
