package org.knowm.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

public class CurrentTimeIncrementalNonceFactory implements SynchronizedValueFactory<Long> {

  private long lastNonce = 0L;

  @Override
  public Long createValue() {

    long newNonce = System.currentTimeMillis();

    while (newNonce <= lastNonce) {
      newNonce++;
    }

    lastNonce = newNonce;

    return newNonce;
  }
}
