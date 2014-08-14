package com.xeiam.xchange.bitfinex.v1;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared Bitfinex properties
 */
public final class BitfinexUtils {

  /**
   * private Constructor
   */
  private BitfinexUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.baseSymbol.toLowerCase() + currencyPair.counterSymbol.toLowerCase();
  }

}
