package org.knowm.xchange.abucoins.service;

import java.util.Base64;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/** @author bryant_harris */
public class AbucoinsDigest extends BaseParamsDigest {

  private AbucoinsDigest(String secretKeyBase64) throws IllegalArgumentException {
    super(
        secretKeyBase64 == null ? null : Base64.getDecoder().decode(secretKeyBase64), HMAC_SHA_256);
  }

  public static AbucoinsDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new AbucoinsDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String timestamp = restInvocation.getHttpHeadersFromParams().get("AC-ACCESS-TIMESTAMP");
    String method = restInvocation.getHttpMethod();
    String path = restInvocation.getPath();
    String queryParameters = restInvocation.getQueryString();
    if (!queryParameters.isEmpty()) {
      queryParameters = '?' + queryParameters;
    }
    String body = restInvocation.getRequestBody();
    body = body == null ? "" : body;

    String queryArgs = timestamp + method + path + (queryParameters + body);
    Mac shaMac = getMac();
    final byte[] macData = shaMac.doFinal(queryArgs.getBytes());
    return Base64.getEncoder().encodeToString(macData);
  }
}
