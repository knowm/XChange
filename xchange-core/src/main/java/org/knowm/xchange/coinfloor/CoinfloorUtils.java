package org.knowm.xchange.coinfloor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.exceptions.ExchangeException;

public final class CoinfloorUtils {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  static {
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private CoinfloorUtils() {
  }

  public static Date parseDate(final String date) {
    try {
      return DATE_FORMAT.parse(date);
    } catch (final ParseException e) {
      throw new ExchangeException("Illegal date/time format: " + date, e);
    }
  }
}
