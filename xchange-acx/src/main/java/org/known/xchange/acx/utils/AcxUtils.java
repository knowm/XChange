package org.known.xchange.acx.utils;

import org.knowm.xchange.currency.CurrencyPair;

public class AcxUtils {
  public static String getAcxMarket(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase()
        + currencyPair.counter.getCurrencyCode().toLowerCase();
  }
}
