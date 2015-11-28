package com.xeiam.xchange.quoine;

import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared Quoine util methods
 */
public final class QuoineUtils {

  /**
   * private Constructor
   */
  private QuoineUtils() {

  }

  private static Map<CurrencyPair, Integer> CURRENCY_PAIR_2_ID_MAP = new HashMap<CurrencyPair, Integer>();

  static {

    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_USD, 1);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_EUR, 3);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_JPY, 5);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_SGD, 7);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_HKD, 9);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_IDR, 11);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_AUD, 13);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_PHP, 15);
  }

  public static int toID(CurrencyPair currencyPair) {
    return CURRENCY_PAIR_2_ID_MAP.get(currencyPair);
  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
  }

}
