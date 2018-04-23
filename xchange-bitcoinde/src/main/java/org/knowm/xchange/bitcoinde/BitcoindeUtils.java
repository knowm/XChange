package org.knowm.xchange.bitcoinde;

import org.knowm.xchange.currency.CurrencyPair;

public class BitcoindeUtils {

  public static String createBitcoindePair(CurrencyPair currencyPair) {

    return currencyPair.getBase().getCurrencyCode().toLowerCase()
        + currencyPair.getCounter().getCurrencyCode().toLowerCase();
  }
}
