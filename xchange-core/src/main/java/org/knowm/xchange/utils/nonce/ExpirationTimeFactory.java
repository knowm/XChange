package org.knowm.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

// Generator for unix timestamp of signature expiration
public class ExpirationTimeFactory implements SynchronizedValueFactory<Long> {

  // Valid time in seconds. Generated timestamp would be current time plus this value
  private long validTime;

  public ExpirationTimeFactory() {
    validTime = 30;
  }

  public ExpirationTimeFactory(long validTime) {
    this.validTime = validTime;
  }

  @Override
  public Long createValue() {
    return System.currentTimeMillis() / 1000 + validTime;
  }
}
