package org.knowm.xchange.empoex.service;

import net.iharder.Base64;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class EmpoExPayloadDigest implements ParamsDigest {

  @Override
  public synchronized String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    return Base64.encodeBytes(postBody.getBytes());
  }
}