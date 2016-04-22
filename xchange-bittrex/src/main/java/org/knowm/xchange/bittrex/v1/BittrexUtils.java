package org.knowm.xchange.bittrex.v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * A central place for shared Bittrex properties
 */
public final class BittrexUtils {

  private static final Date EPOCH = new Date(0);

  private static final Logger logger = LoggerFactory.getLogger(BittrexUtils.class);

  /**
   * private Constructor
   */
  private BittrexUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.counter.getCurrencyCode().toUpperCase() + "-" + currencyPair.base.getCurrencyCode().toUpperCase();
  }

  public static Date toDate(String datetime) {
    // Bittrex can truncate the millisecond component of datetime fields (e.g. 2015-12-20T02:07:51.5)  
    // to the point where if the milliseconds are zero then they are not shown (e.g. 2015-12-26T09:55:23).

    SimpleDateFormat sdf;
    if (datetime.length() == 19) {
      sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    } else {
      sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

    try {
      return sdf.parse(datetime);
    } catch (ParseException e) {
      logger.warn("Unable to parse datetime={}", datetime, e);
      return EPOCH;
    }
  }

}