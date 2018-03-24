package org.knowm.xchange.bleutrade.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class BleutradeDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BleutradeDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BleutradeDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BleutradeDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String invocationUrl = restInvocation.getInvocationUrl();
    Mac mac = getMac();
    mac.update(invocationUrl.getBytes());

    return String.format("%0128x", new BigInteger(1, mac.doFinal()));
  }
}
