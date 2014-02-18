package com.xeiam.xchange.coinbase.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.HeaderParam;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoinbaseDigest implements ParamsDigest {

  private final Mac mac256;

  private CoinbaseDigest(final String secretKey) throws IllegalArgumentException {

    try {
      mac256 = Mac.getInstance("HmacSHA256");
      mac256.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256"));
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code, should be using UTF-8.", e);
    }
  }

  public static CoinbaseDigest createInstance(final String secretKey) throws IllegalArgumentException {

    return secretKey == null ? null : new CoinbaseDigest(secretKey);
  }

  @Override
  public String digestParams(final RestInvocation restInvocation) {

    final String message = restInvocation.getParamValue(HeaderParam.class, "ACCESS_NONCE").toString() 
        + restInvocation.getInvocationUrl() + restInvocation.getRequestBody();

    mac256.update(message.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal()));
  }
}