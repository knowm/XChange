package org.knowm.xchange.coindcx.dto;

import org.knowm.xchange.currency.CurrencyPair;

/** A central place for shared Gemini properties */
public final class CoindcxUtils {

  /** private Constructor */
  private CoindcxUtils() {}

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.base.toString().toLowerCase()
        + currencyPair.counter.toString().toLowerCase();
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

    throw new CoindcxException("Cannot determine withdrawal type.");
  }
}
