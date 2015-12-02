package com.xeiam.xchange.lakebtc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author cristian.lucaci
 */
public class LakeBTCUtil {

  private static long generatedId = 1;

  /**
   * private Constructor
   */
  private LakeBTCUtil() {

  }

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

  /**
   * @deprecated scales of BTCCNY, LTCCNY, LTCBTC are different.
   */
  @Deprecated
  public static BigDecimal truncateAmount(BigDecimal value) {
    return value.setScale(3, RoundingMode.FLOOR).stripTrailingZeros();
  }

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase();
  }
}
