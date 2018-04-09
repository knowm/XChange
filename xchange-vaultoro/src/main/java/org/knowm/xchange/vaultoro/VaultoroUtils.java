package org.knowm.xchange.vaultoro;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/** A central place for shared Vaultoro properties */
public final class VaultoroUtils {

  private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  public static Date parseDate(String dateString) {

    // 2015-04-13T07:56:36.185Z

    format.setTimeZone(TimeZone.getTimeZone("GMT"));

    try {
      return format.parse(dateString);
    } catch (ParseException e) {
      return null;
    }
  }
}
