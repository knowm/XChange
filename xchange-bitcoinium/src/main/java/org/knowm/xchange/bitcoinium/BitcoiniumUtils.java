package org.knowm.xchange.bitcoinium;

import java.util.Arrays;
import java.util.List;

/**
 * A central place for shared Bitcoinium properties
 */
public final class BitcoiniumUtils {

  /**
   * private Constructor
   */
  private BitcoiniumUtils() {

  }

  public static final List<String> PRICE_WINDOW = Arrays.asList(

      "TWO_PERCENT", "FIVE_PERCENT", "TEN_PERCENT", "TWENTY_PERCENT", "FIFTY_PERCENT", "ONE_HUNDRED_PERCENT"

  );

  public static final List<String> TIME_WINDOW = Arrays.asList(

      "ONE_HOUR", "THREE_HOURS", "TWELVE_HOURS", "TWENTY_FOUR_HOURS", "THREE_DAYS", "SEVEN_DAYS", "THIRTY_DAYS", "TWO_MONTHS"

  );

  /**
   * Creates a valid currency pair for Bitcoinium.com
   *
   * @param tradableIdentifier
   * @param currency
   * @return
   */
  public static String createCurrencyPairString(String tradableIdentifier, String currency) {

    return tradableIdentifier + "_" + currency;

  }

  /**
   * Checks if a given PriceWindow is covered by this exchange
   *
   * @param priceWindow
   * @return
   */
  public static boolean isValidPriceWindow(String priceWindow) {

    return PRICE_WINDOW.contains(priceWindow);
  }

  /**
   * Checks if a given TimeWindow is covered by this exchange
   *
   * @param timeWindow
   * @return
   */
  public static boolean isValidTimeWindow(String timeWindow) {

    return TIME_WINDOW.contains(timeWindow);
  }
}
