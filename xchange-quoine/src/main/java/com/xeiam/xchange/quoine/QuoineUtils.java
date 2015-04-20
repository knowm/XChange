package com.xeiam.xchange.quoine;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared Quoine properties
 */
public final class QuoineUtils {

  /**
   * private Constructor
   */
  private QuoineUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.baseSymbol.toLowerCase() + currencyPair.counterSymbol.toLowerCase();
  }

}
