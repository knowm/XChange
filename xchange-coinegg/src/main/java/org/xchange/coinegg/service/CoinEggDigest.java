package org.xchange.coinegg.service;

import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.xchange.coinegg.CoinEggUtils;

import si.mazi.rescu.RestInvocation;

public class CoinEggDigest extends BaseParamsDigest {

  protected CoinEggDigest(String secretKeyBase64) throws IllegalArgumentException {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    throw new NotYetImplementedForExchangeException();
  }
  
  public static CoinEggDigest createInstance(String privateKey) {
    return new CoinEggDigest(CoinEggUtils.toMD5Hash(privateKey));
  }
}
