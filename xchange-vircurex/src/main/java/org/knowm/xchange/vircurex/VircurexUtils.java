package org.knowm.xchange.vircurex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/** A central place for shared Vircurex properties */
public final class VircurexUtils {

  // Vircurex API parameters
  public static final int UNRELEASED_ORDER = 0;
  public static final int RELEASED_ORDER = 1;

  // Vircurex bid/ask syntax
  public static final String BID = "BUY";
  public static final String ASK = "SELL";

  /** private Constructor */
  private VircurexUtils() {}

  public static String getUtcTimestamp() {

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));
    return format.format(new Date());
  }
}
