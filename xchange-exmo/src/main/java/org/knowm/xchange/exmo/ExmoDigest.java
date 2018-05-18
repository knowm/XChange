package org.knowm.xchange.exmo;

import java.math.BigInteger;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class ExmoDigest extends BaseParamsDigest {
  private ExmoDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static ExmoDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new ExmoDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Mac mac = getMac();

    String requestBody = restInvocation.getRequestBody();

    if (requestBody != null && !requestBody.isEmpty()) {
      mac.update(requestBody.getBytes());
    }

    return String.format("%0128x", new BigInteger(1, mac.doFinal()));
  }
}
