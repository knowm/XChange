package org.known.xchange.acx.utils;

import org.knowm.xchange.currency.CurrencyPair;

public class AcxUtils {
  public static String getAcxMarket(CurrencyPair currencyPair) {
    return currencyPair.getBase().getCurrencyCode().toLowerCase()
        + currencyPair.getCounter().getCurrencyCode().toLowerCase();
  }
}
