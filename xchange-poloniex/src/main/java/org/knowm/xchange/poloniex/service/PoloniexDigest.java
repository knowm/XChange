package org.knowm.xchange.poloniex.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class PoloniexDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private PoloniexDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static PoloniexDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new PoloniexDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();

    Mac mac = getMac();
    mac.update(postBody.getBytes());

    return String.format("%0128x", new BigInteger(1, mac.doFinal()));
  }
}
