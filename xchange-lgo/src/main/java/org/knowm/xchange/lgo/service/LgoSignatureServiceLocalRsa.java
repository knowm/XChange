package org.knowm.xchange.lgo.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import lombok.NonNull;
import org.knowm.xchange.lgo.dto.order.LgoOrderSignature;

public class LgoSignatureServiceLocalRsa implements LgoSignatureService {

  private final String apiKey;
  private final String privKey;

  LgoSignatureServiceLocalRsa(@NonNull String apiKey, @NonNull String privKey) {
    this.apiKey = apiKey;
    this.privKey = CryptoUtils.parsePrivateKey(privKey);
  }

  private static String byteArrayToHex(byte[] a) {
    StringBuilder sb = new StringBuilder(a.length * 2);
    for (byte b : a) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

  @Override
  public String digestHeader(String urlToSign, String timestamp) {
    try {
      urlToSign =
          urlToSign
              .replace("http://", "")
              .replace("https://", "")
              .replace("wss://", "")
              .replace("ws://", "");
      String signed = signSHA256RSA(String.format("%s\n%s", timestamp, urlToSign));
      return String.format("LGO %s:%s", apiKey, signed);
    } catch (Exception e) {
      throw new RuntimeException("Error signing request", e);
    }
  }

  @Override
  public LgoOrderSignature signOrder(String encryptedOrder) {
    try {
      return new LgoOrderSignature(signSHA256RSA(encryptedOrder));
    } catch (Exception e) {
      throw new RuntimeException("Error signing order", e);
    }
  }

  private String signSHA256RSA(String input) throws Exception {
    byte[] b1 = Base64.getDecoder().decode(privKey);
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    Signature privateSignature = Signature.getInstance("SHA256withRSA");
    privateSignature.initSign(kf.generatePrivate(spec));
    privateSignature.update(input.getBytes(StandardCharsets.UTF_8));
    return byteArrayToHex(privateSignature.sign());
  }
}
