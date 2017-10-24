package org.knowm.xchange.jubi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.exceptions.ExchangeException;

/**
 * Created by Dzf on 2017/7/16.
 * A central place for shared Jubi properties
 */
public final class JubiUtils {
  private static final SimpleDateFormat DATE_FORMAT;

  static {
    DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+8"));
  }

  private JubiUtils() {

  }

  /**
   * Format a date String for Jubi
   *
   * @param dateString
   * @return
   */
  public static Date parseDate(String dateString) {
    try {
      return dateString == null ? null : DATE_FORMAT.parse(dateString);
    } catch (ParseException e) {
      throw new ExchangeException("Illegal date/time format", e);
    }
  }
}
