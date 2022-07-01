package org.knowm.xchange.independentreserve.util;

import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.lang3.time.FastDateFormat;

public class Util {

  private static final String TIMEZONE = "UTC";
  private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private static final FastDateFormat DATE_FORMAT =
      FastDateFormat.getInstance(PATTERN, TimeZone.getTimeZone(TIMEZONE));

  /**
   * Format a date String for IR
   *
   * @param d a date
   * @return formatted date for Independent Reserve
   */
  public static String formatDate(Date d) {
    return d == null ? null : DATE_FORMAT.format(d);
  }

  public static Date toDate(String date)
      throws com.fasterxml.jackson.databind.exc.InvalidFormatException {
    return org.knowm.xchange.utils.DateUtils.fromISO8601DateString(date);
  }
}
