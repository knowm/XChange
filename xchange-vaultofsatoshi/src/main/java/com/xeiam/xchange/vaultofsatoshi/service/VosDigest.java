package com.xeiam.xchange.vaultofsatoshi.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;
import com.xeiam.xchange.utils.Base64;

/**
 * @author Michael Lagacé
 */
public class VosDigest extends BaseParamsDigest {

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @param clientId
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private VosDigest(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static VosDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new VosDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    // VoS message is url path + 'null character' + request. Ex: "/info/account\000nonce=1234567890"
    String message = "/" + restInvocation.getPath() + "\000" + restInvocation.getRequestBody();

    Mac mac = getMac();
    mac.update(message.getBytes());

    return Base64.encodeBytes((String.format("%064x", new BigInteger(1, mac.doFinal())).getBytes()));
  }
}