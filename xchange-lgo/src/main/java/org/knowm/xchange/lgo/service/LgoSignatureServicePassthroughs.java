package org.knowm.xchange.lgo.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.StringJoiner;
import org.knowm.xchange.lgo.dto.order.LgoOrderSignature;

public class LgoSignatureServicePassthroughs implements LgoSignatureService {

  private final String value;

  public LgoSignatureServicePassthroughs(String userName, String apiKey, String secretKey) {
    String concat = new StringJoiner(":").add(userName).add(apiKey).add(secretKey).toString();
    value = Base64.getEncoder().encodeToString(concat.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String digestSignedUrlHeader(String urlToSign, String timestamp) {
    return value;
  }

  @Override
  public String digestSignedUrlAndBodyHeader(String urlToSign, String timestamp, String body) {
    return value;
  }

  @Override
  public LgoOrderSignature signOrder(String encryptedOrder) {
    return new LgoOrderSignature(value);
  }
}
