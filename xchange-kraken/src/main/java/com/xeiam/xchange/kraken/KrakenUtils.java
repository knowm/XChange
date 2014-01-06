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
package com.xeiam.xchange.kraken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;

public final class KrakenUtils {

  private KrakenUtils() {

  }

  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(

  new CurrencyPair("LTC", "XRP"),

  new CurrencyPair("LTC", "EUR"),

  new CurrencyPair("LTC", "USD"),

  new CurrencyPair("NMC", "XRP"),

  new CurrencyPair("NMC", "EUR"),

  new CurrencyPair("NMC", "USD"),

  new CurrencyPair("BTC", "LTC"),

  new CurrencyPair("BTC", "NMC"),

  new CurrencyPair("BTC", "XRP"),

  new CurrencyPair("BTC", "XVN"),

  new CurrencyPair("BTC", "EUR"),

  new CurrencyPair("BTC", "USD"),

  new CurrencyPair("XVN", "XRP"),

  new CurrencyPair("EUR", "XRP"),

  new CurrencyPair("EUR", "XVN"),

  new CurrencyPair("USD", "XRP"),

  new CurrencyPair("USD", "XRP"),

  new CurrencyPair("LTC", "KRW"),

  new CurrencyPair("NMC", "KRW"),

  new CurrencyPair("BTC", "KRW"),

  new CurrencyPair("KRW", "XRP"),

  new CurrencyPair("USD", "XVN")

  );

  private static Map<String, String> KRAKEN_CURRENCIES_FORWARD = new HashMap<String, String>();
  private static Map<String, String> KRAKEN_CURRENCIES_REVERSE = new HashMap<String, String>();

  static {

    KRAKEN_CURRENCIES_FORWARD.put(Currencies.LTC, "XLTC");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.NMC, "XNMC");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.BTC, "XXBT");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.XBT, "XXBT");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.VEN, "XXVN");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.EUR, "ZEUR");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.USD, "ZUSD");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.XRP, "XXRP");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.KRW, "ZKRW");
    KRAKEN_CURRENCIES_FORWARD.put(Currencies.XVN, "XXVN");

    KRAKEN_CURRENCIES_REVERSE.put("XLTC", Currencies.LTC);
    KRAKEN_CURRENCIES_REVERSE.put("XNMC", Currencies.NMC);
    KRAKEN_CURRENCIES_REVERSE.put("XXBT", Currencies.BTC);
    KRAKEN_CURRENCIES_REVERSE.put("XXVN", Currencies.VEN);
    KRAKEN_CURRENCIES_REVERSE.put("ZEUR", Currencies.EUR);
    KRAKEN_CURRENCIES_REVERSE.put("ZUSD", Currencies.USD);
    KRAKEN_CURRENCIES_REVERSE.put("XXRP", Currencies.XRP);
    KRAKEN_CURRENCIES_REVERSE.put("ZKRW", Currencies.KRW);
    KRAKEN_CURRENCIES_REVERSE.put("XXVN", Currencies.XVN);

    KRAKEN_CURRENCIES_REVERSE.put("LTC", Currencies.LTC);
    KRAKEN_CURRENCIES_REVERSE.put("NMC", Currencies.NMC);
    KRAKEN_CURRENCIES_REVERSE.put("XBT", Currencies.BTC);
    KRAKEN_CURRENCIES_REVERSE.put("XVN", Currencies.VEN);
    KRAKEN_CURRENCIES_REVERSE.put("EUR", Currencies.EUR);
    KRAKEN_CURRENCIES_REVERSE.put("USD", Currencies.USD);
    KRAKEN_CURRENCIES_REVERSE.put("XRP", Currencies.XRP);
    KRAKEN_CURRENCIES_REVERSE.put("KRW", Currencies.KRW);
    KRAKEN_CURRENCIES_REVERSE.put("XVN", Currencies.XVN);
  }

  public static String getKrakenCurrencyCode(String currencyCode) {

    return KRAKEN_CURRENCIES_FORWARD.get(currencyCode);
  }

  public static String getStandardCurrencyCode(String krakenCurrencyCode) {

    return KRAKEN_CURRENCIES_REVERSE.get(krakenCurrencyCode);
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

  public static String getKrakenOrderType(OrderType type) {

    return type == OrderType.ASK ? "sell" : "buy";
  }

  public static long getNonce() {

    return System.currentTimeMillis();
  }
}
