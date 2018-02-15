package org.knowm.xchange.bitflyer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.exceptions.ExchangeException;

public class BitflyerUtils {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  static {
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private BitflyerUtils() {
  }

  public static Date parseDate(final String date) {
    try {
    	  SimpleDateFormat threadSafeClone = (SimpleDateFormat) DATE_FORMAT.clone();
      return threadSafeClone.parse(date);
    } catch (final ParseException e) {
      throw new ExchangeException("Illegal date/time format: " + date, e);
    }
  }
}
