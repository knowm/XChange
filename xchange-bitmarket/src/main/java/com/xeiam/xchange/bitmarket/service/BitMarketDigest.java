package com.xeiam.xchange.bitmarket.service;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class BitMarketDigest implements ParamsDigest {

  private static final String HMAC_SHA_512 = "HmacSHA512";
  private final Mac mac;

  private BitMarketDigest(String secretKeyBase) throws IllegalArgumentException {

    try {

      SecretKey secretKey = new SecretKeySpec(secretKeyBase.getBytes(), HMAC_SHA_512);
      mac = Mac.getInstance(HMAC_SHA_512);
      mac.init(secretKey);

    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }
  }

  public static BitMarketDigest createInstance(String secretKeyBase) {

    return secretKeyBase == null || secretKeyBase.isEmpty() ? null : new BitMarketDigest(secretKeyBase);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String requestBody = restInvocation.getRequestBody();
    mac.update(requestBody.getBytes());
    byte[] bytes = mac.doFinal();
    String format = String.format("%0128x", new BigInteger(1, bytes));
    return format;
  }

}
