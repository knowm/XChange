package org.knowm.xchange.gemini.v1.service;

import java.math.BigInteger;
import java.util.Base64;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class GeminiHmacPostBodyDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private GeminiHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_384);
  }

  public static GeminiHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new GeminiHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    Mac mac = getMac();
    mac.update(Base64.getEncoder().encodeToString(postBody.getBytes()).getBytes());

    return String.format("%096x", new BigInteger(1, mac.doFinal()));
  }
}
