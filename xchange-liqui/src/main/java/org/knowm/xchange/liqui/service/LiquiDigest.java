package org.knowm.xchange.liqui.service;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;

import org.apache.commons.codec.binary.Hex;
import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class LiquiDigest extends BaseParamsDigest {

  protected LiquiDigest(final String secretKeyBase64) throws IllegalArgumentException {
    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static LiquiDigest createInstance(final String secretKeyBase64) {
    if (secretKeyBase64 != null) {
      return new LiquiDigest(secretKeyBase64);
    }
    return null;
  }

  @Override
  public String digestParams(final RestInvocation restInvocation) {

    final Mac mac512 = getMac();
    mac512.update(restInvocation.getRequestBody().getBytes(StandardCharsets.UTF_8));

    return new String(Hex.encodeHex(mac512.doFinal()));
  }
}
