package org.knowm.xchange.bitso;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.FastDateFormat;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared Bitso properties */
public final class BitsoUtils {

  private static final FastDateFormat DATE_FORMAT =
      FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

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
