/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

  public static BigDecimal truncateAmount(BigDecimal value) {

    return value.setScale(3, RoundingMode.FLOOR).stripTrailingZeros();
  }
}