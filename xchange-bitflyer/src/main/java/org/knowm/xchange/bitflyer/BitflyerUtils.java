package org.knowm.xchange.bitflyer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitflyerUtils {

  private static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  private static final SimpleDateFormat DATE_FORMAT_SHORT =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

  static {
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private BitflyerUtils() {}

  public static Date parseDate(final String date) {
    if (date.contains(".")) {
      return parseLongDate(date);
    } else {
      return parseShortDate(date);
    }
  }

  private static Date parseLongDate(final String date) {
    try {
      SimpleDateFormat threadSafeClone = (SimpleDateFormat) DATE_FORMAT.clone();
      return threadSafeClone.parse(date);
    } catch (final ParseException e) {
      throw new ExchangeException("Illegal date/time format: " + date, e);
    }
  }

  private static Date parseShortDate(final String date) {
    try {
      SimpleDateFormat threadSafeClone = (SimpleDateFormat) DATE_FORMAT_SHORT.clone();
      return threadSafeClone.parse(date);
    } catch (final ParseException e) {
      throw new ExchangeException("Illegal date/time format: " + date, e);
    }
  }

  public static String bitflyerProductCode(CurrencyPair pair) {
    return pair.toString().replace("/", "_");
  }
}
