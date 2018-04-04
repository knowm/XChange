package org.knowm.xchange.utils;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacDigest {

  private final Charset utf8 = Charset.forName("UTF-8");
  private final Mac mac;

  public HmacDigest(String algorithm, String secretKey) {
    Assert.notNull(algorithm, "Null algorithm");
    Assert.notNull(secretKey, "Null secretKey");
    try {
      mac = Mac.getInstance(algorithm);
      mac.init(new SecretKeySpec(secretKey.getBytes(utf8), mac.getAlgorithm()));
    } catch (InvalidKeyException | NoSuchAlgorithmException ex) {
      throw new IllegalArgumentException(ex);
    }
  }

  public String hexDigest(String message) {
    byte[] bytes = mac.doFinal(message.getBytes(utf8));
    return DigestUtils.bytesToHex(bytes);
  }
}
