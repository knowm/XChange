package com.xeiam.xchange.bitcoinium;

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

  "2p", "5p", "10p", "20p", "50p", "100p"

  );

  public static final List<String> TIME_WINDOW = Arrays.asList(

  "10m", "1h", "3h", "12h", "24h", "3d", "7d", "30d", "2M"

  );

  /**
   * Creates a valid currency pair for Bitcoinium.com
   * 
   * @param tradableIdentifier
   * @param currency
   * @param exchange
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
