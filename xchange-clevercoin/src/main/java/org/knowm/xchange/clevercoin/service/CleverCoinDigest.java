package org.knowm.xchange.clevercoin.service;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

/**
 * @author Karsten Nilsen
 */
public class CleverCoinDigest extends BaseParamsDigest {

  private final String apiKey;

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private CleverCoinDigest(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static CleverCoinDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new CleverCoinDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac256 = getMac();
    mac256.update(restInvocation.getParamValue(HeaderParam.class, "X-CleverAPI-Nonce").toString().getBytes());
    mac256.update(restInvocation.getParamValue(HeaderParam.class, "X-CleverAPI-Key").toString().getBytes());
    mac256.update(restInvocation.getPath().getBytes());
    // Check if there are GET parameters, if so complete it with the query string
    if (restInvocation.getQueryString().toString().length() != 0) {
      mac256.update("?".getBytes());
      mac256.update(restInvocation.getQueryString().getBytes());
    }

    //return mac256.doFinal().toString();
    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }
}