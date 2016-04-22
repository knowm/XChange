package org.knowm.xchange.poloniex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * @author Zach Holmes
 */

public class PoloniexUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    String pairString = currencyPair.counter.getCurrencyCode().toUpperCase() + "_" + currencyPair.base.getCurrencyCode().toUpperCase();
    return pairString;
  }

  public static CurrencyPair toCurrencyPair(String pair) {

    String[] currencies = pair.split("_");
    return new CurrencyPair(currencies[1], currencies[0]);
  }

  public static Date stringToDate(String dateString) {

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      return sdf.parse(dateString);
    } catch (ParseException e) {
      return new Date(0);
    }
  }
}
