package com.xeiam.xchange.coinbase.service;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;

/**
 * @author jamespedwards42
 */
public class CoinbaseDigest extends BaseParamsDigest {

  private CoinbaseDigest(final String secretKey) {

    super(secretKey, HMAC_SHA_256);
  }

  public static CoinbaseDigest createInstance(final String secretKey) {

    return secretKey == null ? null : new CoinbaseDigest(secretKey);
  }

  @Override
  public String digestParams(final RestInvocation restInvocation) {

    final String message = restInvocation.getParamValue(HeaderParam.class, "ACCESS_NONCE").toString() + restInvocation.getInvocationUrl() + restInvocation.getRequestBody();

    Mac mac256 = getMac();
    mac256.update(message.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal()));
  }
}