package org.knowm.xchange.lgo.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
    } catch (NoSuchAlgorithmException e) {
      throw new ExchangeException("Error encrypting order: algorithm instance not available", e);
    } catch (NoSuchPaddingException e) {
      throw new ExchangeException("Error encrypting order: padding instance not available", e);
    } catch (IllegalBlockSizeException
        | BadPaddingException
        | InvalidKeySpecException
        | InvalidKeyException e) {
      throw new ExchangeException("Error encrypting order: provided data invalid", e);
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
