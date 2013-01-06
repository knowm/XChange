/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.oer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * A central place for shared OER properties
 */
public class OERUtils {

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair(Currencies.AED, Currencies.USD), new CurrencyPair(Currencies.AFN, Currencies.USD), new CurrencyPair(Currencies.ALL, Currencies.USD),
      new CurrencyPair(Currencies.AMD, Currencies.USD), new CurrencyPair(Currencies.ANG, Currencies.USD), new CurrencyPair(Currencies.AOA, Currencies.USD), new CurrencyPair(Currencies.ARS,
          Currencies.USD), new CurrencyPair(Currencies.AUD, Currencies.USD), new CurrencyPair(Currencies.AWG, Currencies.USD), new CurrencyPair(Currencies.AZN, Currencies.USD), new CurrencyPair(
          Currencies.BAM, Currencies.USD), new CurrencyPair(Currencies.BBD, Currencies.USD), new CurrencyPair(Currencies.BDT, Currencies.USD), new CurrencyPair(Currencies.BGN, Currencies.USD),
      new CurrencyPair(Currencies.BHD, Currencies.USD), new CurrencyPair(Currencies.BIF, Currencies.USD), new CurrencyPair(Currencies.BMD, Currencies.USD), new CurrencyPair(Currencies.BND,
          Currencies.USD), new CurrencyPair(Currencies.BOB, Currencies.USD), new CurrencyPair(Currencies.BRL, Currencies.USD), new CurrencyPair(Currencies.BSD, Currencies.USD), new CurrencyPair(
          Currencies.BTN, Currencies.USD), new CurrencyPair(Currencies.BWP, Currencies.USD), new CurrencyPair(Currencies.BYR, Currencies.USD), new CurrencyPair(Currencies.BZD, Currencies.USD),
      new CurrencyPair(Currencies.CAD, Currencies.USD), new CurrencyPair(Currencies.CDF, Currencies.USD), new CurrencyPair(Currencies.CHF, Currencies.USD), new CurrencyPair(Currencies.CLF,
          Currencies.USD), new CurrencyPair(Currencies.CLP, Currencies.USD), new CurrencyPair(Currencies.CNY, Currencies.USD), new CurrencyPair(Currencies.COP, Currencies.USD), new CurrencyPair(
          Currencies.CRC, Currencies.USD), new CurrencyPair(Currencies.CUP, Currencies.USD), new CurrencyPair(Currencies.CVE, Currencies.USD), new CurrencyPair(Currencies.CZK, Currencies.USD),
      new CurrencyPair(Currencies.DJF, Currencies.USD), new CurrencyPair(Currencies.DKK, Currencies.USD), new CurrencyPair(Currencies.DOP, Currencies.USD), new CurrencyPair(Currencies.DZD,
          Currencies.USD), new CurrencyPair(Currencies.EEK, Currencies.USD), new CurrencyPair(Currencies.EGP, Currencies.USD), new CurrencyPair(Currencies.ETB, Currencies.USD), new CurrencyPair(
          Currencies.EUR, Currencies.USD), new CurrencyPair(Currencies.FJD, Currencies.USD), new CurrencyPair(Currencies.FKP, Currencies.USD), new CurrencyPair(Currencies.GBP, Currencies.USD),
      new CurrencyPair(Currencies.GEL, Currencies.USD), new CurrencyPair(Currencies.GHS, Currencies.USD), new CurrencyPair(Currencies.GIP, Currencies.USD), new CurrencyPair(Currencies.GMD,
          Currencies.USD), new CurrencyPair(Currencies.GNF, Currencies.USD), new CurrencyPair(Currencies.GTQ, Currencies.USD), new CurrencyPair(Currencies.GYD, Currencies.USD), new CurrencyPair(
          Currencies.HKD, Currencies.USD), new CurrencyPair(Currencies.HNL, Currencies.USD), new CurrencyPair(Currencies.HRK, Currencies.USD), new CurrencyPair(Currencies.HTG, Currencies.USD),
      new CurrencyPair(Currencies.HUF, Currencies.USD), new CurrencyPair(Currencies.IDR, Currencies.USD), new CurrencyPair(Currencies.ILS, Currencies.USD), new CurrencyPair(Currencies.INR,
          Currencies.USD), new CurrencyPair(Currencies.IQD, Currencies.USD), new CurrencyPair(Currencies.IRR, Currencies.USD), new CurrencyPair(Currencies.ISK, Currencies.USD), new CurrencyPair(
          Currencies.JEP, Currencies.USD), new CurrencyPair(Currencies.JMD, Currencies.USD), new CurrencyPair(Currencies.JOD, Currencies.USD), new CurrencyPair(Currencies.JPY, Currencies.USD),
      new CurrencyPair(Currencies.KES, Currencies.USD), new CurrencyPair(Currencies.KGS, Currencies.USD), new CurrencyPair(Currencies.KHR, Currencies.USD), new CurrencyPair(Currencies.KMF,
          Currencies.USD), new CurrencyPair(Currencies.KPW, Currencies.USD), new CurrencyPair(Currencies.KRW, Currencies.USD), new CurrencyPair(Currencies.KWD, Currencies.USD), new CurrencyPair(
          Currencies.KYD, Currencies.USD), new CurrencyPair(Currencies.KZT, Currencies.USD), new CurrencyPair(Currencies.LAK, Currencies.USD), new CurrencyPair(Currencies.LBP, Currencies.USD),
      new CurrencyPair(Currencies.LKR, Currencies.USD), new CurrencyPair(Currencies.LRD, Currencies.USD), new CurrencyPair(Currencies.LSL, Currencies.USD), new CurrencyPair(Currencies.LTL,
          Currencies.USD), new CurrencyPair(Currencies.LVL, Currencies.USD), new CurrencyPair(Currencies.LYD, Currencies.USD), new CurrencyPair(Currencies.MAD, Currencies.USD), new CurrencyPair(
          Currencies.MDL, Currencies.USD), new CurrencyPair(Currencies.MGA, Currencies.USD), new CurrencyPair(Currencies.MKD, Currencies.USD), new CurrencyPair(Currencies.MMK, Currencies.USD),
      new CurrencyPair(Currencies.MNT, Currencies.USD), new CurrencyPair(Currencies.MOP, Currencies.USD), new CurrencyPair(Currencies.MRO, Currencies.USD), new CurrencyPair(Currencies.MUR,
          Currencies.USD), new CurrencyPair(Currencies.MVR, Currencies.USD), new CurrencyPair(Currencies.MWK, Currencies.USD), new CurrencyPair(Currencies.MXN, Currencies.USD), new CurrencyPair(
          Currencies.MYR, Currencies.USD), new CurrencyPair(Currencies.MZN, Currencies.USD), new CurrencyPair(Currencies.NAD, Currencies.USD), new CurrencyPair(Currencies.NGN, Currencies.USD),
      new CurrencyPair(Currencies.NIO, Currencies.USD), new CurrencyPair(Currencies.NOK, Currencies.USD), new CurrencyPair(Currencies.NPR, Currencies.USD), new CurrencyPair(Currencies.NZD,
          Currencies.USD), new CurrencyPair(Currencies.OMR, Currencies.USD), new CurrencyPair(Currencies.PAB, Currencies.USD), new CurrencyPair(Currencies.PEN, Currencies.USD), new CurrencyPair(
          Currencies.PGK, Currencies.USD), new CurrencyPair(Currencies.PHP, Currencies.USD), new CurrencyPair(Currencies.PKR, Currencies.USD), new CurrencyPair(Currencies.PLN, Currencies.USD),
      new CurrencyPair(Currencies.PYG, Currencies.USD), new CurrencyPair(Currencies.QAR, Currencies.USD), new CurrencyPair(Currencies.RON, Currencies.USD), new CurrencyPair(Currencies.RSD,
          Currencies.USD), new CurrencyPair(Currencies.RUB, Currencies.USD), new CurrencyPair(Currencies.RWF, Currencies.USD), new CurrencyPair(Currencies.SAR, Currencies.USD), new CurrencyPair(
          Currencies.SBD, Currencies.USD), new CurrencyPair(Currencies.SCR, Currencies.USD), new CurrencyPair(Currencies.SDG, Currencies.USD), new CurrencyPair(Currencies.SEK, Currencies.USD),
      new CurrencyPair(Currencies.SGD, Currencies.USD), new CurrencyPair(Currencies.SHP, Currencies.USD), new CurrencyPair(Currencies.SLL, Currencies.USD), new CurrencyPair(Currencies.SOS,
          Currencies.USD), new CurrencyPair(Currencies.SRD, Currencies.USD), new CurrencyPair(Currencies.STD, Currencies.USD), new CurrencyPair(Currencies.SVC, Currencies.USD), new CurrencyPair(
          Currencies.SYP, Currencies.USD), new CurrencyPair(Currencies.SZL, Currencies.USD), new CurrencyPair(Currencies.THB, Currencies.USD), new CurrencyPair(Currencies.TJS, Currencies.USD),
      new CurrencyPair(Currencies.TMT, Currencies.USD), new CurrencyPair(Currencies.TND, Currencies.USD), new CurrencyPair(Currencies.TOP, Currencies.USD), new CurrencyPair(Currencies.TRY,
          Currencies.USD), new CurrencyPair(Currencies.TTD, Currencies.USD), new CurrencyPair(Currencies.TWD, Currencies.USD), new CurrencyPair(Currencies.TZS, Currencies.USD), new CurrencyPair(
          Currencies.UAH, Currencies.USD), new CurrencyPair(Currencies.UGX, Currencies.USD), new CurrencyPair(Currencies.USD, Currencies.USD), new CurrencyPair(Currencies.UYU, Currencies.USD),
      new CurrencyPair(Currencies.UZS, Currencies.USD), new CurrencyPair(Currencies.VEF, Currencies.USD), new CurrencyPair(Currencies.VND, Currencies.USD), new CurrencyPair(Currencies.VUV,
          Currencies.USD), new CurrencyPair(Currencies.WST, Currencies.USD), new CurrencyPair(Currencies.XAF, Currencies.USD), new CurrencyPair(Currencies.XCD, Currencies.USD), new CurrencyPair(
          Currencies.XDR, Currencies.USD), new CurrencyPair(Currencies.XOF, Currencies.USD), new CurrencyPair(Currencies.XPF, Currencies.USD), new CurrencyPair(Currencies.YER, Currencies.USD),
      new CurrencyPair(Currencies.ZAR, Currencies.USD), new CurrencyPair(Currencies.ZMK, Currencies.USD), new CurrencyPair(Currencies.ZWL, Currencies.USD));

  public static final int REFRESH_RATE = 10; // [seconds]

  /**
   * Converts a currency and long price into a BigMoney Object
   * 
   * @param currency
   * @param price
   * @return
   */
  public static BigMoney getPrice(String currency, float price) {

    return MoneyUtils.parseFiat(currency + " " + new BigDecimal(price));
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

}
