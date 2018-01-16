package org.knowm.xchange.kuna.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author Dat Bui
 */
public class KunaUtils {

  private static final String DATE_FORMAT_NO_MILLIS = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  /**
   * Hide default constructor.
   */
  private KunaUtils() {
  }

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase() + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

  public static Date toDate(String dateString) {
    try {
      return dateParserNoMillis().parse(dateString);
    } catch (ParseException e1) {
      throw new ExchangeException("Illegal date/time format", e1);
    }
  }

  public static String format(Date date) {
    return dateParserNoMillis().format(date);
  }

  private static SimpleDateFormat dateParserNoMillis() {
    SimpleDateFormat dateParserNoMillis = new SimpleDateFormat(DATE_FORMAT_NO_MILLIS);
    return dateParserNoMillis;
  }
}
