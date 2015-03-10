package com.xeiam.xchange.loyalbit.service;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;

public class LoyalbitDigest extends BaseParamsDigest {

  private final String clientId;
  private final String apiKey;

  private LoyalbitDigest(String secretKeyBase64, String clientId, String apiKey) {
    super(secretKeyBase64, HMAC_SHA_256);
    this.clientId = clientId;
    this.apiKey = apiKey;
  }

  public static LoyalbitDigest createInstance(String secretKeyBase64, String clientId, String apiKey) {
    return secretKeyBase64 == null ? null : new LoyalbitDigest(secretKeyBase64, clientId, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Mac mac256 = getMac();
    mac256.update(restInvocation.getInvocationUrl().getBytes());
    mac256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }
}