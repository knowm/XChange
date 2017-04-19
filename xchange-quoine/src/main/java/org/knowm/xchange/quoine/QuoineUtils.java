package org.knowm.xchange.quoine;

import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * A central place for shared Quoine util methods
 */
public final class QuoineUtils {

  /**
   * private Constructor
   */
  private QuoineUtils() {

  }

  private static Map<CurrencyPair, Integer> CURRENCY_PAIR_2_ID_MAP = new HashMap<>();

  static {
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_USD, 1);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_EUR, 3);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_JPY, 5);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_SGD, 7);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_HKD, 9);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_IDR, 11);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_AUD, 13);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_PHP, 15);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_CNY, 17);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.BTC_INR, 18);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.ETH_USD, 27);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.ETH_EUR, 28);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.ETH_JPY, 29);
    CURRENCY_PAIR_2_ID_MAP.put(new CurrencyPair("ETH", "SGD"), 30);
    CURRENCY_PAIR_2_ID_MAP.put(new CurrencyPair("ETH", "HKD"), 31);
    CURRENCY_PAIR_2_ID_MAP.put(new CurrencyPair("ETH", "IDR"), 32);
    CURRENCY_PAIR_2_ID_MAP.put(new CurrencyPair("ETH", "AUD"), 33);
    CURRENCY_PAIR_2_ID_MAP.put(new CurrencyPair("ETH", "PHP"), 34);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.ETH_CNY, 35);
    CURRENCY_PAIR_2_ID_MAP.put(new CurrencyPair("ETH", "INR"), 36);
    CURRENCY_PAIR_2_ID_MAP.put(CurrencyPair.ETH_BTC, 37);
  }

  public static int toID(CurrencyPair currencyPair) {
    return CURRENCY_PAIR_2_ID_MAP.get(currencyPair);
  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
  }

}
