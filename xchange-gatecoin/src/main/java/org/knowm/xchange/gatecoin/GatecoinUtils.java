package org.knowm.xchange.gatecoin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared Gatecoin properties */
public final class GatecoinUtils {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /** private Constructor */
  private GatecoinUtils() {}

  /**
   * Format a date String for Gatecoin
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

  public static Date parseUnixTSToDateTime(String dateString) {

    try {
      long unixSeconds = Long.valueOf(dateString);
      Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
      return DATE_FORMAT.parse(DATE_FORMAT.format(date));
    } catch (ParseException e) {
      throw new ExchangeException("Illegal date/time format", e);
    }
  }
}
