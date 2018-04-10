package org.knowm.xchange.abucoins.service;

import org.knowm.xchange.abucoins.Abucoins;
import org.knowm.xchange.abucoins.dto.AbucoinsServerTime;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import java.io.IOException;
import java.util.Base64;

/** @author bryant_harris */
public class AbucoinsDigest extends BaseParamsDigest {
  private final long timeDiffFromServer;

  private AbucoinsDigest(Abucoins abucoins, String secretKeyBase64) throws IllegalArgumentException {
    super(secretKeyBase64 == null ? null : Base64.getDecoder().decode(secretKeyBase64), HMAC_SHA_256);

    try {
      AbucoinsServerTime serverTime = abucoins.getTime();

      long ourTime = System.currentTimeMillis() / 1000L;
      timeDiffFromServer = serverTime.getEpoch() - ourTime;
    } catch (IOException e) {
      throw new RuntimeException("Unable to determine server time");
    }
  }

  public static AbucoinsDigest createInstance(Abucoins abucoins, String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new AbucoinsDigest(abucoins, secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String timestamp = restInvocation.getHttpHeadersFromParams().get("AC-ACCESS-TIMESTAMP");
    String method = restInvocation.getHttpMethod();
    String path = restInvocation.getPath();
    String queryParameters = restInvocation.getQueryString();
    String body = restInvocation.getRequestBody();
    body = body == null ? "" : body;

    String queryArgs = timestamp + method + path + (queryParameters + body);
    Mac shaMac = getMac();
    final byte[] macData = shaMac.doFinal(queryArgs.getBytes());
    return Base64.getEncoder().encodeToString(macData);
  }

  public String timestamp() {
    return String.valueOf((System.currentTimeMillis() / 1000) + timeDiffFromServer);
  }
}
