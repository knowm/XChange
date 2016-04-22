package org.knowm.xchange.mexbt.dto;

import org.knowm.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTRequest {

  protected final String apiKey;
  protected final long apiNonce;
  protected final String apiSig;

  public MeXBTRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest) {
    this.apiKey = apiKey;
    final Long nonce;
    synchronized (nonceFactory) {
      nonce = nonceFactory.createValue();
    }
    this.apiNonce = nonce.longValue();
    this.apiSig = meXBTDigest.digestParams(apiNonce);
  }

  public String getApiKey() {
    return apiKey;
  }

  public long getApiNonce() {
    return apiNonce;
  }

  public String getApiSig() {
    return apiSig;
  }

}
