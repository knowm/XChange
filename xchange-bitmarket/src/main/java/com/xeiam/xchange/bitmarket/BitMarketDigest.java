package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @author kfonal
 */
public class BitMarketDigest extends BaseParamsDigest {

  private BitMarketDigest(String secretKeyBase64) throws IllegalArgumentException {
    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BitMarketDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BitMarketDigest(secretKeyBase64);
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
    //mac.update(restInvocation.getParamValue(FormParam.class, "method").toString().getBytes());
    //mac.update(restInvocation.getParamValue(FormParam.class, "tonce").toString().getBytes());

    //return String.format("%0128x", new BigInteger(1, mac.doFinal())).toUpperCase();
  }
}
