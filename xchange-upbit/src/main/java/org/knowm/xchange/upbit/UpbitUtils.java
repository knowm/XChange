package org.knowm.xchange.upbit;

import org.knowm.xchange.currency.CurrencyPair;

public class UpbitUtils {

  public static final String MARKET_NAME_SEPARATOR = "-";

  private UpbitUtils() {
    // not called
  }

  public static String toPairString(CurrencyPair currencyPair) {
    if (currencyPair == null) return null;
    return currencyPair.counter.getCurrencyCode().toUpperCase()
        + MARKET_NAME_SEPARATOR
        + currencyPair.base.getCurrencyCode().toUpperCase();
  }

  public static CurrencyPair toCurrencyPair(String pairString) {
    if (pairString == null) return null;
    String[] pairStringSplit = pairString.split(MARKET_NAME_SEPARATOR);
    if (pairStringSplit.length != 2) return null;
    return new CurrencyPair(pairStringSplit[1], pairStringSplit[0]);
  }
}
