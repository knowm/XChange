package org.knowm.xchange.coindeal.service;

import si.mazi.rescu.BasicAuthCredentials;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoindealDigest implements ParamsDigest {

  private final String secret;
  private final String apiKey;

  private CoindealDigest(String secret, String apiKey) {
    this.secret = secret;
    this.apiKey = apiKey;
  }

  public static CoindealDigest createInstance(String secret, String apiKey) {
    if (secret != null || apiKey != null) {
      return new CoindealDigest(secret, apiKey);
    } else return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    return new BasicAuthCredentials(apiKey, secret).digestParams(restInvocation);
  }
}
