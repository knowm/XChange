package org.knowm.xchange.cryptofacilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.knowm.xchange.utils.DateUtils;

public class Util {

  public static Date parseDate(String str) {
    try {
      return str == null ? null : DateUtils.fromISODateString(str);
    } catch (Exception e) {
      throw new RuntimeException("Could not parse date using '" + str + "'.", e);
    }
  }

  public static String format(Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    return f.format(date);
  }
}
