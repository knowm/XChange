package com.xeiam.xchange.btce.v3;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared BTC-E properties
 */
public final class BTCEUtils {

  /**
   * private Constructor
   */
  private BTCEUtils() {

  }

  public static String getPair(CurrencyPair currencyPair) {

    return currencyPair.baseSymbol.toLowerCase() + "_" + currencyPair.counterSymbol.toLowerCase();
  }

}
