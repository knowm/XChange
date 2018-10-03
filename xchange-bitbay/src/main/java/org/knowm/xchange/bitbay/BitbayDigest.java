package org.knowm.xchange.bitbay;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/** @author kfonal */
public class BitbayDigest extends BaseParamsDigest {

  private BitbayDigest(String secretKeyBase64) throws IllegalArgumentException {
    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BitbayDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BitbayDigest(secretKeyBase64);
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
