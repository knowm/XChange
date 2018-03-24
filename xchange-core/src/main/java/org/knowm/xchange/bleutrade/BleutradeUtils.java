package org.knowm.xchange.bleutrade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.knowm.xchange.currency.CurrencyPair;

public final class BleutradeUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.base.getCurrencyCode().toUpperCase() + "_" + currencyPair.counter.getCurrencyCode().toUpperCase();
  }

  public static CurrencyPair toCurrencyPair(String pairString) {

    String[] currencies = pairString.split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static Date toDate(String timeStamp) {

    try {

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'.'SSS");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      return sdf.parse(timeStamp);

    } catch (ParseException e) {

      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.parse(timeStamp);

      } catch (ParseException e1) {

        return null;
      }
    }
  }
}
