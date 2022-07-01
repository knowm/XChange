package org.knowm.xchange.bithumb.service;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BithumbEndpointGenerator implements ParamsDigest {

  @Override
  public String digestParams(RestInvocation restInvocation) {
    return restInvocation.getPath();
  }
}
