package org.knowm.xchange.cryptocom;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.knowm.xchange.cryptocom.dto.CryptoComRequest;
import org.knowm.xchange.utils.DigestUtils;

/**
 * Builds and signs {@link CryptoComRequest} envelopes for Crypto.com Exchange v1 private endpoints.
 * The signature is an HMAC-SHA256, hex-encoded, of {@code method + id + api_key + sortedParamString
 * + nonce}, where {@code sortedParamString} recursively flattens {@code params} (sorted by key,
 * nested maps/iterables flattened the same way) with no separators.
 */
public final class CryptoComDigest {

  private static final String HMAC_SHA256 = "HmacSHA256";

  private CryptoComDigest() {}

  public static CryptoComRequest sign(
      String method,
      long id,
      long nonce,
      String apiKey,
      String apiSecret,
      Map<String, Object> params) {
    String payload = signaturePayload(method, id, apiKey, nonce, params);
    String signature = hmacSha256Hex(payload, apiSecret);
    return new CryptoComRequest(id, method, apiKey, signature, nonce, params);
  }

  private static String signaturePayload(
      String method, long id, String apiKey, long nonce, Map<String, Object> params) {
    StringBuilder paramString = new StringBuilder();
    if (params != null && !params.isEmpty()) {
      TreeMap<String, Object> sortedParams = new TreeMap<>(params);
      for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
        paramString.append(entry.getKey());
        paramString.append(formatValue(entry.getValue()));
      }
    }
    return method + id + apiKey + paramString + nonce;
  }

  @SuppressWarnings("unchecked")
  private static String formatValue(Object value) {
    if (value == null) {
      return "";
    }
    if (value instanceof Map) {
      StringBuilder sb = new StringBuilder();
      TreeMap<String, Object> sorted = new TreeMap<>((Map<String, Object>) value);
      for (Map.Entry<String, Object> entry : sorted.entrySet()) {
        sb.append(entry.getKey());
        sb.append(formatValue(entry.getValue()));
      }
      return sb.toString();
    }
    if (value instanceof Iterable) {
      StringBuilder sb = new StringBuilder();
      for (Object item : (Iterable<?>) value) {
        sb.append(formatValue(item));
      }
      return sb.toString();
    }
    return value.toString();
  }

  private static String hmacSha256Hex(String data, String secret) {
    try {
      Mac mac = Mac.getInstance(HMAC_SHA256);
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
      byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return DigestUtils.bytesToHex(hmacBytes);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new IllegalStateException("Failed to generate Crypto.com HMAC signature", e);
    }
  }
}
