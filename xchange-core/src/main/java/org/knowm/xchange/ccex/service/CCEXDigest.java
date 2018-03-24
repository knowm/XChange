package org.knowm.xchange.ccex.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class CCEXDigest extends BaseParamsDigest {

  private CCEXDigest(String secretKey) {

    super(secretKey, HMAC_SHA_512);
  }

  public static CCEXDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new CCEXDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String invocationUrl = restInvocation.getInvocationUrl();
    Mac mac = getMac();
    mac.update(invocationUrl.getBytes());

    return String.format("%0128x", new BigInteger(1, mac.doFinal()));
  }

}
