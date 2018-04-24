package org.knowm.xchange.bleutrade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class BleutradeUtils {

  public static String toPairString(org.knowm.xchange.currency.CurrencyPair currencyPair) {

    return currencyPair.getBase().getCurrencyCode().toUpperCase()
        + "_"
        + currencyPair.getCounter().getCurrencyCode().toUpperCase();
  }

  public static org.knowm.xchange.currency.CurrencyPair toCurrencyPair(String pairString) {

    String[] currencies = pairString.split("_");
    return org.knowm.xchange.currency.CurrencyPair.build(currencies[0], currencies[1]);
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
