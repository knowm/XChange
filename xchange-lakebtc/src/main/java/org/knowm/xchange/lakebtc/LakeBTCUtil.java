package org.knowm.xchange.lakebtc;

import org.knowm.xchange.currency.CurrencyPair;

/** @author cristian.lucaci */
public class LakeBTCUtil {

  private static long generatedId = 1;

  /** private Constructor */
  private LakeBTCUtil() {}

  public static long getGeneratedId() {
    return generatedId++;
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase();
  }
}
