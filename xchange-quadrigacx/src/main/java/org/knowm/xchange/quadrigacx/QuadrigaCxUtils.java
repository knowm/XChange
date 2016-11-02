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
        // SimpleDateFormat is not thread safe, there fore create a new instance every time
        SimpleDateFormat df = new SimpleDateFormat(PATTERN);
        df.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
      return df.parse(dateString);
    } catch (ParseException e) {
      throw new ExchangeException("Illegal date/time format", e);
    }
  }

  public static String currencyPairToBook(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase() + "_" + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

}
