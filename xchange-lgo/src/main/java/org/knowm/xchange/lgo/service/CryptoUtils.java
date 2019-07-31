package org.knowm.xchange.lgo.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lgo.dto.key.LgoKey;
import org.knowm.xchange.lgo.dto.order.LgoPlaceOrder;

public final class CryptoUtils {

  private CryptoUtils() {}

  public static String encryptOrder(LgoKey lgoKey, LgoPlaceOrder lgoPlaceOrder) {
    try {
      String pub = lgoKey.getValue();
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      byte[] keyBytes = Base64.getDecoder().decode(pub.getBytes(StandardCharsets.UTF_8));
      X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
      RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);

      Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] cipherData =
          cipher.doFinal(lgoPlaceOrder.toPayload().getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(cipherData);
    } catch (Exception e) {
      throw new ExchangeException("Error encrypting order", e);
    }
  }

  static String parsePrivateKey(String key) {
    return key.replaceAll("-----END PRIVATE KEY-----", "")
        .replaceAll("-----BEGIN PRIVATE KEY-----", "")
        .replaceAll("\n", "");
  }

  static String parsePublicKey(String key) {
    return key.replaceAll("-----END PUBLIC KEY-----", "")
        .replaceAll("-----BEGIN PUBLIC KEY-----", "")
        .replaceAll("\n", "");
  }
}
