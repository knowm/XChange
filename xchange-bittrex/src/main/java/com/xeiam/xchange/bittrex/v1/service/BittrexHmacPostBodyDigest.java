package com.xeiam.xchange.bittrex.v1.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.utils.Base64;

import com.xeiam.xchange.service.BaseParamsDigest;

public class BittrexHmacPostBodyDigest extends BaseParamsDigest {

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BittrexHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BittrexHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BittrexHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    System.out.println("postBody = " + postBody);
    Mac mac = getMac();
    mac.update(Base64.encodeBytes(postBody.getBytes()).getBytes());

    return String.format("%0128x", new BigInteger(1, mac.doFinal()));
  }
}
