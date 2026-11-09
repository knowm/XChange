package org.knowm.xchange.upbit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.QueryParam;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

/** Signs Upbit requests with an HS256 JSON Web Token carrying the access key, nonce and query. */
public class UpbitJWTDigest implements ParamsDigest {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final Base64.Encoder BASE64_URL = Base64.getUrlEncoder().withoutPadding();
  private static final String HEADER = base64Url("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");

  private final String accessKey;
  private final byte[] secretKey;

  private UpbitJWTDigest(String accessKey, String secretKey) throws IllegalArgumentException {
    this.accessKey = accessKey;
    this.secretKey = secretKey.getBytes(StandardCharsets.UTF_8);
  }

  public static UpbitJWTDigest createInstance(String accessKey, String secretKey) {
    return new UpbitJWTDigest(accessKey, secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String queryString = "";
    if (restInvocation.getParamsMap().get(QueryParam.class) != null
        && !restInvocation.getParamsMap().get(QueryParam.class).isEmpty()) {
      queryString = String.valueOf(restInvocation.getParamsMap().get(QueryParam.class));
    } else if (restInvocation.getRequestBody() != null
        && !restInvocation.getRequestBody().isEmpty()) {
      try {
        Map<String, String> map = MAPPER.readValue(restInvocation.getRequestBody(), Map.class);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
          sb.append('&').append(entry.getKey()).append('=').append(entry.getValue());
        }
        queryString = sb.length() > 0 ? sb.substring(1) : "";
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }

    Map<String, String> claims = new LinkedHashMap<>();
    claims.put("access_key", accessKey);
    claims.put("nonce", UUID.randomUUID().toString());
    if (!queryString.isEmpty()) {
      claims.put("query", queryString);
    }
    return "Bearer " + createToken(claims);
  }

  /** Builds a compact HS256 JWT: base64url(header).base64url(payload).base64url(signature). */
  String createToken(Map<String, String> claims) {
    try {
      String payload = base64Url(MAPPER.writeValueAsString(claims));
      String signingInput = HEADER + "." + payload;
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secretKey, "HmacSHA256"));
      byte[] signature = mac.doFinal(signingInput.getBytes(StandardCharsets.UTF_8));
      return signingInput + "." + BASE64_URL.encodeToString(signature);
    } catch (IOException | GeneralSecurityException e) {
      throw new IllegalStateException("Failed to create Upbit JWT", e);
    }
  }

  private static String base64Url(String value) {
    return BASE64_URL.encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }
}
