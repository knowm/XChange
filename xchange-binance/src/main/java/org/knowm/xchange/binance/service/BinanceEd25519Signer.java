package org.knowm.xchange.binance.service;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/** Ed25519 signing for Binance's Ed25519 API keys, using only the JDK's built-in provider. */
public final class BinanceEd25519Signer {

  private static final String ALGORITHM = "Ed25519";

  private BinanceEd25519Signer() {}

  /**
   * Parses a base64-encoded PKCS#8 Ed25519 private key, which is the format Binance hands out.
   *
   * @throws IllegalArgumentException if the key is not valid base64 or not a PKCS#8 Ed25519 key
   */
  public static PrivateKey parsePrivateKey(String base64Pkcs8PrivateKey) {
    try {
      byte[] pkcs8 = Base64.getDecoder().decode(base64Pkcs8PrivateKey);
      return KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(pkcs8));
    } catch (GeneralSecurityException | IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid Binance Ed25519 private key", e);
    }
  }

  /** Signs the UTF-8 bytes of {@code payload} and returns the signature as base64. */
  public static String signBase64(PrivateKey privateKey, String payload) {
    try {
      Signature signature = Signature.getInstance(ALGORITHM);
      signature.initSign(privateKey);
      signature.update(payload.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(signature.sign());
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException("Ed25519 signing failed", e);
    }
  }
}
