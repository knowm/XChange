package org.knowm.xchange.empoex;

import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;

public final class EmpoExUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.getBase().getCurrencyCode()
        + "-"
        + currencyPair.getCounter().getCurrencyCode();
  }

  public static CurrencyPair toCurrencyPair(String pairString) {

    String[] currencies = pairString.split("-");
    return CurrencyPair.build(currencies[0], currencies[1]);
  }

  public static Date toDate(long unix) {

    return new Date(unix * 1000L);
  }

  public static Date toDate(String dateString) {

    return toDate(Long.parseLong(dateString));
  }
}
