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
package com.xeiam.xchange.kraken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;

public final class KrakenUtils {

  private KrakenUtils() {

  }

  public static final Set<CurrencyPair> CURRENCY_PAIRS = new HashSet<CurrencyPair>(Arrays.asList(CurrencyPair.LTC_XRP, CurrencyPair.LTC_EUR, CurrencyPair.LTC_USD, CurrencyPair.LTC_KRW,
      CurrencyPair.BTC_LTC, CurrencyPair.BTC_NMC, CurrencyPair.BTC_XRP, CurrencyPair.BTC_XVN, CurrencyPair.BTC_EUR, CurrencyPair.BTC_USD, CurrencyPair.BTC_KRW, CurrencyPair.NMC_XRP,
      CurrencyPair.NMC_EUR, CurrencyPair.NMC_USD, CurrencyPair.NMC_KRW, CurrencyPair.XVN_XRP, CurrencyPair.USD_XVN, CurrencyPair.EUR_XVN, CurrencyPair.EUR_XRP, CurrencyPair.USD_XRP,
      CurrencyPair.KRW_XRP));

  private static Map<String, String> KRAKEN_CURRENCIES_FORWARD = new HashMap<String, String>();
  private static Map<String, String> KRAKEN_CURRENCIES_REVERSE = new HashMap<String, String>();

  static {

    KRAKEN_CURRENCIES_FORWARD.put(Currencies.LTC, "XLTC");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.NMC, "XNMC");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.BTC, "XXBT");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.XBT, "XXBT");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.EUR, "ZEUR");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.USD, "ZUSD");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.XRP, "XXRP");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.KRW, "ZKRW");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.XVN, "XXVN");

    KRAKEN_CURRENCIES_REVERSE.put("XLTC", Currencies.LTC);
    KRAKEN_CURRENCIES_REVERSE.put("XNMC", Currencies.NMC);
    KRAKEN_CURRENCIES_REVERSE.put("XXBT", Currencies.BTC);
    KRAKEN_CURRENCIES_REVERSE.put("ZEUR", Currencies.EUR);
    KRAKEN_CURRENCIES_REVERSE.put("ZUSD", Currencies.USD);
    KRAKEN_CURRENCIES_REVERSE.put("XXRP", Currencies.XRP);
    KRAKEN_CURRENCIES_REVERSE.put("ZKRW", Currencies.KRW);
    KRAKEN_CURRENCIES_REVERSE.put("XXVN", Currencies.XVN);

    KRAKEN_CURRENCIES_REVERSE.put(Currencies.LTC, Currencies.LTC);
    KRAKEN_CURRENCIES_REVERSE.put(Currencies.NMC, Currencies.NMC);
    KRAKEN_CURRENCIES_REVERSE.put(Currencies.XBT, Currencies.BTC);
    KRAKEN_CURRENCIES_REVERSE.put(Currencies.EUR, Currencies.EUR);
    KRAKEN_CURRENCIES_REVERSE.put(Currencies.USD, Currencies.USD);
    KRAKEN_CURRENCIES_REVERSE.put(Currencies.XRP, Currencies.XRP);
    KRAKEN_CURRENCIES_REVERSE.put(Currencies.KRW, Currencies.KRW);
    KRAKEN_CURRENCIES_REVERSE.put(Currencies.XVN, Currencies.XVN);
  }

  public static String getKrakenCurrencyCode(String currencyCode) {

    return KRAKEN_CURRENCIES_FORWARD.get(currencyCode);
  }

  public static String getStandardCurrencyCode(String krakenCurrencyCode) {

    return KRAKEN_CURRENCIES_REVERSE.get(krakenCurrencyCode);
  }

  public static String createKrakenCurrencyPair(CurrencyPair currencyPair) {

    return createKrakenCurrencyPair(currencyPair.baseCurrency, currencyPair.counterCurrency);
  }

  public static String createKrakenCurrencyPair(String tradableIdentifier, String currency) {

    String currency1 = KRAKEN_CURRENCIES_FORWARD.get(tradableIdentifier);
    if (currency1 == null) {
      currency1 = tradableIdentifier;
    }
    String currency2 = KRAKEN_CURRENCIES_FORWARD.get(currency);
    if (currency2 == null) {
      currency2 = currency;
    }
    return currency1 + currency2;
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

  public static KrakenType getKrakenOrderType(OrderType type) {

    return type == OrderType.ASK ? KrakenType.SELL : KrakenType.BUY;
  }

  public static long getNonce() {

    return System.currentTimeMillis();
  }
}
