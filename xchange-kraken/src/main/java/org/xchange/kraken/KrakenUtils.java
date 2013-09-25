package org.xchange.kraken;

import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.currency.Currencies;

public final class KrakenUtils {

  private KrakenUtils() {

  }

  private static Map<String, String> krakenCurrencies = new HashMap<String, String>();
  private static Map<String,String> currencies=new HashMap<String,String>();
  static {
    krakenCurrencies.put(Currencies.BTC, "XXBT");
    krakenCurrencies.put(Currencies.LTC, "XLTC");
    krakenCurrencies.put(Currencies.EUR, "ZEUR");
    krakenCurrencies.put(Currencies.USD, "ZUSD");
    currencies.put("XXBT", Currencies.BTC);
    currencies.put("XLTC", Currencies.LTC);
    currencies.put("ZEUR", Currencies.EUR);
    currencies.put("ZUSD", Currencies.USD);

  }
  public static String getKrakenCurrencyCode(String currencyCode){
    return krakenCurrencies.get(currencyCode);
  }
  public static String getCurrency(String krakenCurrencyCode){
    return currencies.get(krakenCurrencyCode);
  }
  public static String createKrakenCurrencyPair(String tradableIdentifier, String currency) {

    String currency1 = krakenCurrencies.get(tradableIdentifier);
    if (currency1 == null) {
      currency1 = tradableIdentifier;
    }
    String currency2 = krakenCurrencies.get(currency);
    if (currency2 == null) {
      currency2 = currency;
    }
    return currency1+currency2;
  }
  public static long getNonce(){
    return System.currentTimeMillis();
  }
}
