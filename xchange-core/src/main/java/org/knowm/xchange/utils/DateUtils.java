package org.knowm.xchange.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * <p>
 * Utilities to provide the following to application:
 * </p>
 * <ul>
 * <li>Provision of standard date and time handling</li>
 * </ul>
 */
public class DateUtils {

  /**
   * private Constructor
   */
  private DateUtils() {

  }

  /**
   * Creates a date from a long representing milliseconds from epoch
   *
   * @param millisecondsFromEpoch
   * @return the Date object
   */
  public static Date fromMillisUtc(long millisecondsFromEpoch) {

    return new Date(millisecondsFromEpoch);
  }

  /**
   * Converts a date to a UTC String representation
   *
   * @param date
   * @return the formatted date
   */
  public static String toUTCString(Date date) {

    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    sd.setTimeZone(TimeZone.getTimeZone("GMT"));
    return sd.format(date);
  }

  /**
   * Converts an ISO formatted Date String to a Java Date ISO format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
   *
   * @param isoFormattedDate
   * @return Date
   * @throws InvalidFormatException
   */
  public static Date fromISODateString(String isoFormattedDate) throws InvalidFormatException {

    SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    // set UTC time zone - 'Z' indicates it
    isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      return isoDateFormat.parse(isoFormattedDate);
    } catch (ParseException e) {
      throw new InvalidFormatException("Error parsing as date", isoFormattedDate, Date.class);
    }
  }

  /**
   * Converts an ISO 8601 formatted Date String to a Java Date ISO 8601 format: yyyy-MM-dd'T'HH:mm:ss
   *
   * @param iso8601FormattedDate
   * @return Date
   * @throws InvalidFormatException
   */
  public static Date fromISO8601DateString(String iso8601FormattedDate) throws InvalidFormatException {

    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    try {
      return iso8601Format.parse(iso8601FormattedDate);
    } catch (ParseException e) {
      throw new InvalidFormatException("Error parsing as date", iso8601FormattedDate, Date.class);
    }
  }

  /**
   * Converts an rfc1123 formatted Date String to a Java Date rfc1123 format: EEE, dd MMM yyyy HH:mm:ss zzz
   *
   * @param rfc1123FormattedDate
   * @return Date
   * @throws InvalidFormatException
   */
  public static Date fromRfc1123DateString(String rfc1123FormattedDate, Locale locale) throws InvalidFormatException {

    SimpleDateFormat rfc1123DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", locale);
    try {
      return rfc1123DateFormat.parse(rfc1123FormattedDate);
    } catch (ParseException e) {
      throw new InvalidFormatException("Error parsing as date", rfc1123FormattedDate, Date.class);
    }
  }

  /**
   * Convert java time long to unix time long, simply by dividing by 1000
   */
  public static long toUnixTime(long javaTime) {
    return javaTime / 1000;
  }

  /**
   * Convert java time to unix time long, simply by dividing by the time 1000
   */
  public static long toUnixTime(Date time) {
    return time.getTime() / 1000;
  }

  /**
   * Convert java time to unix time long, simply by dividing by the time 1000. Null safe
   */
  public static Long toUnixTimeNullSafe(Date time) {

    return time == null ? null : time.getTime() / 1000;
  }

  public static Long toMillisNullSafe(Date time) {

    return time == null ? null : time.getTime();
  }

  /**
   * Convert unix time to Java Date
   */
  public static Date fromUnixTime(long unix) {
    return new Date(unix * 1000);
  }

}
