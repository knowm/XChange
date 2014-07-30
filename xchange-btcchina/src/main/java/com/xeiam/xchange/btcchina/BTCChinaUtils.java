package com.xeiam.xchange.btcchina;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import com.xeiam.xchange.btcchina.dto.BTCChinaValue;

/**
 * @author ObsessiveOrange
 *         A central place for shared BTCChina properties
 */
public final class BTCChinaUtils {

  private static long generatedId = 1;
  private static long lastNonce = 0l;

  /**
   * private Constructor
   */
  private BTCChinaUtils() {

  }

  public synchronized static long getNonce() {

    long newNonce = System.currentTimeMillis() * 1000;

    while (newNonce == lastNonce) {
      newNonce++;
    }

    lastNonce = newNonce;

    return newNonce;
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

  public static BigDecimal valueToBigDecimal(BTCChinaValue value) {

    return new BigDecimal(new BigInteger(value.getAmountInteger()), value.getAmountDecimal().intValue());
  }

  /**
   * @deprecated scales of BTCCNY, LTCCNY, LTCBTC are different.
   */
  @Deprecated
  public static BigDecimal truncateAmount(BigDecimal value) {

    return value.setScale(3, RoundingMode.FLOOR).stripTrailingZeros();
  }

}