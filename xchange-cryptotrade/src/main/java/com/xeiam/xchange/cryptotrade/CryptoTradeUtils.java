package com.xeiam.xchange.cryptotrade;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared CryptoTrade properties
 */
public final class CryptoTradeUtils {

  /**
   * private Constructor
   */
  private CryptoTradeUtils() {

  }

  public static String getCryptoTradeCurrencyPair(String tradeCurrency, String priceCurrency) {

    return tradeCurrency.toLowerCase() + "_" + priceCurrency.toLowerCase();

  }

  public static String getCryptoTradeCurrencyPair(CurrencyPair currencyPair) {

    return getCryptoTradeCurrencyPair(currencyPair.baseSymbol, currencyPair.counterSymbol);
  }
}
