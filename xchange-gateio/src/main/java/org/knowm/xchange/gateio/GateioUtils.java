package org.knowm.xchange.gateio;

import org.knowm.xchange.currency.CurrencyPair;

public class GateioUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    String baseSymbol = currencyPair.base.getCurrencyCode().toLowerCase();
    String counterSymbol = currencyPair.counter.getCurrencyCode().toLowerCase();
    String pair = baseSymbol + "_" + counterSymbol;

    return pair;
  }
}
