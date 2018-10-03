package org.knowm.xchange.bitfinex.common.service;

import java.util.Base64;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BitfinexPayloadDigest implements ParamsDigest {

  @Override
  public synchronized String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    return Base64.getEncoder().encodeToString(postBody.getBytes());
  }
}
