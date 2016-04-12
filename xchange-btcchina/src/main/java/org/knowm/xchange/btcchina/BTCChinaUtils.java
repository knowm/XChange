package org.knowm.xchange.btcchina;

import static org.knowm.xchange.service.BaseParamsDigest.HMAC_SHA_1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.knowm.xchange.btcchina.dto.BTCChinaValue;

/**
 * @author ObsessiveOrange A central place for shared BTCChina properties
 */
public final class BTCChinaUtils {

  private static long generatedId = 1;

  /**
   * private Constructor
   */
  private BTCChinaUtils() {

  }

  public static long getGeneratedId() {

    return generatedId++;
  }

  public static String getSignature(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {

    // get an hmac_sha1 key from the raw key bytes
    SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA_1);

    // get an hmac_sha1 Mac instance and initialize with the signing key
    Mac mac = Mac.getInstance(HMAC_SHA_1);
    mac.init(signingKey);

    // compute the hmac on input data bytes
    byte[] rawHmac = mac.doFinal(data.getBytes());

    return bytesToHex(rawHmac);
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

}