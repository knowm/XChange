package org.knowm.xchange.ccex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.knowm.xchange.currency.CurrencyPair;

public class CCEXUtils {

  private static final Date EPOCH = new Date(0);

  private CCEXUtils() {}

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.counter.getCurrencyCode().toLowerCase()
        + "-"
        + currencyPair.base.getCurrencyCode().toLowerCase();
  }

  public static Date toDate(String datetime) {
    SimpleDateFormat sdf;

    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

    try {
      return sdf.parse(datetime);
    } catch (ParseException e) {
      return EPOCH;
    }
  }
}
