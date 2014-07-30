package com.xeiam.xchange.bitstamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xeiam.xchange.ExchangeException;

/**
 * A central place for shared Bitstamp properties
 */
public final class BitstampUtils {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * private Constructor
   */
  private BitstampUtils() {

  }

  /**
   * Format a date String for Bitstamp
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

  public static long getNonce() {

    return System.currentTimeMillis();
  }

}
