package com.xeiam.xchange.bittrex.v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * A central place for shared Bittrex properties
 */
public final class BittrexUtils {

  /**
   * private Constructor
   */
  private BittrexUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.counter.getCurrencyCode().toUpperCase() + "-" + currencyPair.base.getCurrencyCode().toUpperCase();
  }

  public static Date toDate(String timeStamp) {

    try {

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      return sdf.parse(timeStamp);

    } catch (ParseException e) {

      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.parse(timeStamp);

      } catch (ParseException e1) {

        e1.printStackTrace();
        return new Date(0L);

      }
    }
  }

}