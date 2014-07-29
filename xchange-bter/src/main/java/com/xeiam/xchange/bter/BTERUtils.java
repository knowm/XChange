package com.xeiam.xchange.bter;

import com.xeiam.xchange.currency.CurrencyPair;

public class BTERUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    String baseSymbol = currencyPair.baseSymbol.toLowerCase();
    String counterSymbol = currencyPair.counterSymbol.toLowerCase();
    String pair = baseSymbol + "_" + counterSymbol;

    return pair;
  }
}
