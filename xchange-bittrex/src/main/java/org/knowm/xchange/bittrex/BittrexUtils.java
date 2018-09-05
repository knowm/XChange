package org.knowm.xchange.bittrex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared Bittrex properties */
public final class BittrexUtils {

  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
  private static final String DATE_FORMAT_NO_MILLIS = "yyyy-MM-dd'T'HH:mm:ss";

  private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

  /** private Constructor */
  private BittrexUtils() {}

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.counter.getCurrencyCode().toUpperCase()
        + "-"
        + currencyPair.base.getCurrencyCode().toUpperCase();
  }

  public static CurrencyPair toCurrencyPair(String pairString) {
    String[] pairStringSplit = pairString.split("-");
    return new CurrencyPair(pairStringSplit[1], pairStringSplit[0]);
  }

  public static Date toDate(String dateString) {

    if (dateString == null) return null;

    try {
      return dateParser().parse(dateString);
    } catch (ParseException e) {
      try {
        return dateParserNoMillis().parse(dateString);
      } catch (ParseException e1) {
        throw new ExchangeException("Illegal date/time format", e1);
      }
    }
  }

  private static SimpleDateFormat dateParserNoMillis() {
    SimpleDateFormat dateParserNoMillis = new SimpleDateFormat(DATE_FORMAT_NO_MILLIS);
    dateParserNoMillis.setTimeZone(TIME_ZONE);
    return dateParserNoMillis;
  }

  private static SimpleDateFormat dateParser() {
    SimpleDateFormat dateParser = new SimpleDateFormat(DATE_FORMAT);
    dateParser.setTimeZone(TIME_ZONE);
    return dateParser;
  }
}
