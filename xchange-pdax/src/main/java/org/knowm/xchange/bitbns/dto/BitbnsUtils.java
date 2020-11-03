package org.knowm.xchange.bitbns.dto;

import org.knowm.xchange.currency.CurrencyPair;

public final class BitbnsUtils {

  /** private Constructor */
  private BitbnsUtils() {}

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.base.toString().toUpperCase()
        + "_"
        + currencyPair.counter.toString().toUpperCase();
  }

  /**
   * can be “bitcoin”, “litecoin” or “ethereum” or “tether” or “wire”.
   *
   * @param currency
   * @return
   */
  public static String convertToGeminiCcyName(String currency) {

    if (currency.toUpperCase().equals("BTC")) return "btc";
    if (currency.toUpperCase().equals("ETH")) return "eth";

    throw new BitbnsException("Cannot determine withdrawal type.");
  }
}
