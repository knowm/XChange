package org.knowm.xchange.vaultoro.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class VaultoroDigest extends BaseParamsDigest {

  private VaultoroDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static VaultoroDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new VaultoroDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String invocationUrl = restInvocation.getInvocationUrl();
    Mac mac = getMac();
    mac.update(invocationUrl.getBytes());

    return String.format("%040x", new BigInteger(1, mac.doFinal()));
  }
}
