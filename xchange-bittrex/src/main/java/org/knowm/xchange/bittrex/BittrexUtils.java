package org.knowm.xchange.bittrex;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

/** A central place for shared Bittrex utility operations */
public final class BittrexUtils {

  public static final String MARKET_NAME_SEPARATOR = "-";

  public static String toPairString(Instrument currencyPair) {
    if (currencyPair == null) return null;
    return currencyPair.getBase().getCurrencyCode().toUpperCase()
        + MARKET_NAME_SEPARATOR
        + currencyPair.getCounter().getCurrencyCode().toUpperCase();
  }

  public static CurrencyPair toCurrencyPair(String pairString) {
    if (pairString == null) return null;
    String[] pairStringSplit = pairString.split(MARKET_NAME_SEPARATOR);
    if (pairStringSplit.length != 2) return null;
    return new CurrencyPair(pairStringSplit[0], pairStringSplit[1]);
  }

  /** Utility class */
  private BittrexUtils() {
    throw new AssertionError();
  }
}
