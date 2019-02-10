package org.knowm.xchange.coinsuper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

  /**
   * @param timeStamp
   * @return
   */
  public static Date timeStampToDate(long timeStamp) {
    java.util.Date date = new java.util.Date((long) timeStamp * 1000);

    return date;
  }

  /**
   * @param unixSeconds
   * @return
   */
  public static String unixTimeToDate(long unixSeconds) {
    if (unixSeconds > 0) {
      Date date = new java.util.Date(unixSeconds * 1000L);

      SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      // dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));

      return dateFormat.format(date);
    } else {
      return "";
    }
  }
}
