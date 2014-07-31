package com.xeiam.xchange.poloniex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author Zach Holmes
 */

public class PoloniexUtils {

  public static String toPairString(CurrencyPair currencyPair) {

    String pairString = currencyPair.counterSymbol.toUpperCase() + "_" + currencyPair.baseSymbol.toUpperCase();
    return pairString;
  }

  public static CurrencyPair toCurrencyPair(String pair) {

    String[] currencies = pair.split("_");
    return new CurrencyPair(currencies[1], currencies[0]);
  }

  public static Date stringToDate(String dateString) {

    try {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
    } catch (ParseException e) {
      return new Date(0);
    }
  }
}
