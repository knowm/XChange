package org.known.xchange.dsx.service;

import static org.knowm.xchange.service.BaseParamsDigest.HMAC_SHA_512;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

/**
 * @author Mikhail Wall
 */

public class DSXHmacPostBodyDigest extends BaseParamsDigest {

  private DSXHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static DSXHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new DSXHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    try {
      String postBody = restInvocation.getRequestBody();
      Mac mac = getMac();
      mac.update(postBody.getBytes("UTF-8"));
      return String.format("%0128x", new BigInteger(1, mac.doFinal()));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    }
  }
}
