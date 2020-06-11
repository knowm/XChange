package org.knowm.xchange.bittrex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** A central place for shared Bittrex properties */
public final class BittrexUtils {

  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
  private static final String DATE_FORMAT_NO_MILLIS = "yyyy-MM-dd'T'HH:mm:ss";

  private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
  protected static final String MARKET_NAME_SEPARATOR = "-";

  /** private Constructor */
  private BittrexUtils() {}

  /**
   * @deprecated Name if confusing (no api version specification), use a toPairString with a version
   *     specifier since Bittrex changed the market names order.
   *     <p>use {@link #toPairString(CurrencyPair, boolean)} ()} instead
   */
  @Deprecated
  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.counter.getCurrencyCode().toUpperCase()
        + "-"
        + currencyPair.base.getCurrencyCode().toUpperCase();
  }

  /**
   * The market names format is reversed starting api version 3.
   *
   * @param currencyPair the market name
   * @param apiVersion3 true if api version is 3 or more
   * @return the market name string representation
   */
  public static String toPairString(CurrencyPair currencyPair, boolean apiVersion3) {
    Stream<Currency> currencies =
        apiVersion3
            ? Stream.of(currencyPair.base, currencyPair.counter)
            : Stream.of(currencyPair.counter, currencyPair.base);
    return currencies
        .map(Currency::getCurrencyCode)
        .collect(Collectors.joining(MARKET_NAME_SEPARATOR))
        .toUpperCase();
  }

  /**
   * The market names format is reversed starting api version 3.
   *
   * @param pairString the Bittrex string representation of a market
   * @param apiVersion3 true if api version is 3 or more
   * @return the corresponding CurrencyPair
   */
  public static CurrencyPair toCurrencyPair(String pairString, boolean apiVersion3) {
    String[] pairStringSplit = pairString.split(MARKET_NAME_SEPARATOR);
    if (apiVersion3) {
      return new CurrencyPair(pairStringSplit[0], pairStringSplit[1]);
    }
    return new CurrencyPair(pairStringSplit[1], pairStringSplit[0]);
  }

  /**
   * @deprecated Name if confusing (no api version specification), use a toPairString with a version
   *     specifier since Bittrex changed the market names order.
   *     <p>use {@link #toCurrencyPair(String, boolean)} ()} instead
   */
  @Deprecated
  public static CurrencyPair toCurrencyPair(String pairString) {
    String[] pairStringSplit = pairString.split(MARKET_NAME_SEPARATOR);
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
