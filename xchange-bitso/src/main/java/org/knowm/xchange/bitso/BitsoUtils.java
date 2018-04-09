package org.knowm.xchange.bitso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared Bitso properties */
public final class BitsoUtils {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /** private Constructor */
  private BitsoUtils() {}

  /**
   * Format a date String for Bitso
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
