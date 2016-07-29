package org.knowm.xchange.loyalbit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.knowm.xchange.exceptions.ExchangeException;

/**
 * A central place for shared Loyalbit properties
 */
public final class LoyalbitUtils {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * private Constructor
   */
  private LoyalbitUtils() {

  }

  /**
   * Format a date String for Loyalbit
   *
   * @param dateString
   * @return
   */
  public static Date parseDate(String dateString) {

    try {
      return DATE_FORMAT.parse(dateString);
    } catch (ParseException e) {
      throw new ExchangeException("Illegal date/time format", e);
    }
  }

}
