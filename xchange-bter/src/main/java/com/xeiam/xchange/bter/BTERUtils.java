package com.xeiam.xchange.bter;

import com.xeiam.xchange.currency.CurrencyPair;

public class BTERUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    String baseSymbol = currencyPair.base.getCurrencyCode().toLowerCase();
    String counterSymbol = currencyPair.counter.getCurrencyCode().toLowerCase();
    String pair = baseSymbol + "_" + counterSymbol;

    return pair;
  }
}
