package com.xeiam.xchange.justcoin;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * jamespedwards42
 */
public final class JustcoinUtils {

  /**
   * private Constructor
   */
  private JustcoinUtils() {

  }

  public static String getApiMarket(final CurrencyPair currencyPair) {

    return getApiMarket(currencyPair.baseSymbol, currencyPair.counterSymbol);
  }

  public static String getApiMarket(final String tradableIdentifier, final String currency) {

    return tradableIdentifier + currency;
  }
}
