package org.knowm.xchange.bitcoinde;

import org.knowm.xchange.currency.CurrencyPair;

public class BitcoindeUtils {

  public static String createBitcoindePair(CurrencyPair currencyPair) {

    return currencyPair.base.getCurrencyCode().toLowerCase()
        + currencyPair.counter.getCurrencyCode().toLowerCase();
  }
}
