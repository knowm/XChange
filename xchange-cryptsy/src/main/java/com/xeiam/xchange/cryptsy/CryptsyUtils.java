package com.xeiam.xchange.cryptsy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author ObsessiveOrange
 */
public final class CryptsyUtils {

  private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * private Constructor
   */
  private CryptsyUtils() {

  }

  public static Date convertDateTime(String dateTime) throws ParseException {

    dateFormatter.setTimeZone(TimeZone.getTimeZone("EST"));

    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
  }

}
