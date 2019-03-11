package org.knowm.xchange.acx.utils;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class AcxUtils {
  public static String getAcxMarket(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase()
        + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

  public static CurrencyPair getCurrencyPair(String acxMarket) {
    return new CurrencyPair(
        Currency.getInstance(acxMarket.substring(0, 3)),
        Currency.getInstance(acxMarket.substring(3, 6)));
  }
}
