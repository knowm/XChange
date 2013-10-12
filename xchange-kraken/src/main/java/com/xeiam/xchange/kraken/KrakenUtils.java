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

import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;

public final class KrakenUtils {

  private KrakenUtils() {

  }

  private static Map<String, String> krakenCurrencies = new HashMap<String, String>();
  private static Map<String, String> currencies = new HashMap<String, String>();
  static {
    krakenCurrencies.put(Currencies.BTC, "XXBT");
    krakenCurrencies.put(Currencies.LTC, "XLTC");
    krakenCurrencies.put(Currencies.EUR, "ZEUR");
    krakenCurrencies.put(Currencies.USD, "ZUSD");
    currencies.put("XXBT", Currencies.BTC);
    currencies.put("XLTC", Currencies.LTC);
    currencies.put("ZEUR", Currencies.EUR);
    currencies.put("ZUSD", Currencies.USD);

  }

  public static String getKrakenCurrencyCode(String currencyCode) {

    return krakenCurrencies.get(currencyCode);
  }

  public static String getCurrency(String krakenCurrencyCode) {

    return currencies.get(krakenCurrencyCode);
  }

  public static String createKrakenCurrencyPair(String tradableIdentifier, String currency) {

    String currency1 = krakenCurrencies.get(tradableIdentifier);
    if (currency1 == null) {
      currency1 = tradableIdentifier;
    }
    String currency2 = krakenCurrencies.get(currency);
    if (currency2 == null) {
      currency2 = currency;
    }
    return currency1 + currency2;
  }

  public static String getKrakenOrderType(OrderType type) {

    return type == OrderType.ASK ? "sell" : "buy";
  }

  public static long getNonce() {

    return System.currentTimeMillis();
  }
}
