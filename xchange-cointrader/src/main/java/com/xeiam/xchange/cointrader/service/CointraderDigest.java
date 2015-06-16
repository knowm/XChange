package com.xeiam.xchange.cointrader.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;

public class CointraderDigest extends BaseParamsDigest {

  public CointraderDigest(String secretKey) {
    super(secretKey.getBytes(), HMAC_SHA_256);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    return digest(restInvocation.getRequestBody());
  }

  String digest(String requestBody) {
    Mac mac256 = getMac();
    mac256.update(requestBody.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal()));
  }
}