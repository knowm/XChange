package org.knowm.xchange.quadrigacx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * A central place for shared QuadrigaCx properties
 */
public final class QuadrigaCxUtils {

  private static final String TIMEZONE = "UTC";
  private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final SimpleDateFormat DATE_FORMAT;

  static {
    DATE_FORMAT = new SimpleDateFormat(PATTERN);
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
  }

  /**
   * private Constructor
   */
  private QuadrigaCxUtils() {

  }

  /**
   * Format a date String for QuadrigaCx
   *
   * @param dateString
   * @return
   */
  public static Date parseDate(String dateString) {
    try {
      synchronized (DATE_FORMAT) { // SimpleDateFormat is not thread safe, therefore synchronize it
        return DATE_FORMAT.parse(dateString);
      }
    } catch (ParseException e) {
      throw new ExchangeException("Illegal date/time format", e);
    }
  }

  public static String currencyPairToBook(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase() + "_" + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

}
