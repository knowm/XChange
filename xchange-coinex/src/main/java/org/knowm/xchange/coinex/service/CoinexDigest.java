package org.knowm.xchange.coinex.service;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.BasicAuthCredentials;
import si.mazi.rescu.RestInvocation;

public class CoinexDigest extends BaseParamsDigest {

  private ThreadLocal<byte[]> threadLocalMac;
  private final String secret;
  private final String apiKey;

  private CoinexDigest(String secretKeyBase64, String apiKey) {
    super(secretKeyBase64, HMAC_MD5);
    this.secret = secretKeyBase64;
    this.apiKey = apiKey;
  }

  public static CoinexDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new CoinexDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    return new BasicAuthCredentials(apiKey, secret).digestParams(restInvocation);
  }
}
