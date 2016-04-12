package org.knowm.xchange.cryptsy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author ObsessiveOrange
 */
public final class CryptsyUtils {

  /**
   * private Constructor
   */
  private CryptsyUtils() {

  }

  public static Date convertDateTime(String dateTime) throws ParseException {

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormatter.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
    return dateFormatter.parse(dateTime);
  }

}
