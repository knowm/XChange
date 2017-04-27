package org.knowm.xchange.bittrex.v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * A central place for shared Bittrex properties
 */
public final class BittrexUtils {

  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat(DATE_FORMAT);

  static {
    DATE_PARSER.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  /**
   * private Constructor
   */
  private BittrexUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.counter.getCurrencyCode().toUpperCase() + "-" + currencyPair.base.getCurrencyCode().toUpperCase();
  }

  public static Date toDate(String dateString) {
    try {
      return DATE_PARSER.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
      return new Date();
    }
  }

}