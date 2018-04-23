package org.knowm.xchange.empoex;

import java.util.Date;

public final class EmpoExUtils {

  public static String toPairString(org.knowm.xchange.currency.CurrencyPair currencyPair) {

    return currencyPair.getBase().getCurrencyCode()
        + "-"
        + currencyPair.getCounter().getCurrencyCode();
  }

  public static org.knowm.xchange.currency.CurrencyPair toCurrencyPair(String pairString) {

    String[] currencies = pairString.split("-");
    return org.knowm.xchange.currency.CurrencyPair.build(currencies[0], currencies[1]);
  }

  public static Date toDate(long unix) {

    return new Date(unix * 1000L);
  }

  public static Date toDate(String dateString) {

    return toDate(Long.parseLong(dateString));
  }
}
