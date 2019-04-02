package org.knowm.xchange.quadrigacx;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.lang3.time.FastDateFormat;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared QuadrigaCx properties */
public final class QuadrigaCxUtils {

  private static final String TIMEZONE = "UTC";
  private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final FastDateFormat DATE_FORMAT =
      FastDateFormat.getInstance(PATTERN, TimeZone.getTimeZone(TIMEZONE));

  /** private Constructor */
  private QuadrigaCxUtils() {}

  /**
   * Format a date String for QuadrigaCx
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

  public static String currencyPairToBook(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase()
        + "_"
        + currencyPair.counter.getCurrencyCode().toLowerCase();
  }
}
