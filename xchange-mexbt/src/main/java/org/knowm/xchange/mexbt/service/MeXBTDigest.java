package org.knowm.xchange.mexbt.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class MeXBTDigest extends BaseParamsDigest {

  private final String userId;
  private final String publicKey;

  private MeXBTDigest(String secretKeyBase64, String userId, String publicKey) {
    super(secretKeyBase64, HMAC_SHA_256);
    this.userId = userId;
    this.publicKey = publicKey;
  }

  public static MeXBTDigest createInstance(String secretKeyBase64, String userId, String publicKey) {
    return secretKeyBase64 == null ? null : new MeXBTDigest(secretKeyBase64, userId, publicKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    throw new UnsupportedOperationException();
  }

  public String digestParams(long nonce) {
    Mac mac = getMac();
    mac.update(String.valueOf(nonce).toString().getBytes());
    mac.update(userId.getBytes());
    mac.update(publicKey.getBytes());
    return String.format("%064x", new BigInteger(1, mac.doFinal())).toUpperCase();
  }

}