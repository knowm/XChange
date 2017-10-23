package org.knowm.xchange.gemini.v1;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gemini.v1.dto.GeminiException;

/**
 * A central place for shared Gemini properties
 */
public final class GeminiUtils {

  /**
   * private Constructor
   */
  private GeminiUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.base.toString().toLowerCase() + currencyPair.counter.toString().toLowerCase();
  }

  /**
   * can be “bitcoin”, “litecoin” or “ethereum” or “tether” or “wire”.
   *
   * @param currency
   * @return
   */
  public static String convertToGeminiCcyName(String currency) {

    if (currency.toUpperCase().equals("BTC"))
      return "btc";
    if (currency.toUpperCase().equals("ETH"))
      return "eth";

    throw new GeminiException("Cannot determine withdrawal type.");
  }
}
