package org.knowm.xchange.exx.utils;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CommonUtil {

  /**
   * @param timeStamp
   * @return
   */
  public static Date timeStampToDate(long timeStamp) {
    java.util.Date date = new java.util.Date((long) timeStamp * 1000);

    return date;
  }

  /**
   * @param unixSeconds
   * @return
   */
  public static String unixTimeToDate(long unixSeconds) {
    if (unixSeconds > 0) {
      Date date = new java.util.Date(unixSeconds * 1000L);

      SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      // dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));

      return dateFormat.format(date);
    } else {
      return "";
    }
  }

  /**
   * HmacSHA512
   *
   * @param String value
   * @param String secret
   * @return String
   */
  public static String HmacSHA512(String value, String secret) {
    String result;
    try {
      Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
      SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
      hmacSHA512.init(secretKeySpec);

      byte[] digest = hmacSHA512.doFinal(value.getBytes());
      BigInteger hash = new BigInteger(1, digest);
      result = hash.toString(16);
      if ((result.length() % 2) != 0) {
        result = "0" + result;
      }
    } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
      throw new RuntimeException("Problem as HMAC", ex);
    }
    return result;
  }
}
