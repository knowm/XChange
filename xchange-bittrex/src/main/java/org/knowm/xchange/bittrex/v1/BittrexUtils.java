package org.knowm.xchange.bittrex.v1;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A central place for shared Bittrex properties
 */
public final class BittrexUtils {

  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
  private static final String DATE_FORMAT_NO_MILLIS = "yyyy-MM-dd'T'HH:mm:ss";

  private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat(DATE_FORMAT);
  private static final SimpleDateFormat DATE_PARSER_NO_MILLIS = new SimpleDateFormat(DATE_FORMAT_NO_MILLIS);

  private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

  static {
    DATE_PARSER.setTimeZone(TIME_ZONE);
    DATE_PARSER_NO_MILLIS.setTimeZone(TIME_ZONE);
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
      try {
        return DATE_PARSER_NO_MILLIS.parse(dateString);
      } catch (ParseException e1) {
        throw new ExchangeException("Illegal date/time format", e1);
      }
    }
  }

}