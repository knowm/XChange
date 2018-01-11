package org.knowm.xchange.independentreserve.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Util {

  private static final String TIMEZONE = "UTC";
  private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private static final SimpleDateFormat DATE_FORMAT;

  static {
    DATE_FORMAT = new SimpleDateFormat(PATTERN);
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
  }

  private Util() {
  }

  /**
   * Format a date String for IR
   *
   * @param d a date
   * @return formatted date for Independent Reserve
   */
  public static String formatDate(Date d) {
    synchronized (DATE_FORMAT) {       // SimpleDateFormat is not thread safe, therefore synchronize it
      return d == null ? null : DATE_FORMAT.format(d);
    }
  }

  public static Date toDate(String date) throws ParseException {
    return FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mmX").parse(date);
  }

}
