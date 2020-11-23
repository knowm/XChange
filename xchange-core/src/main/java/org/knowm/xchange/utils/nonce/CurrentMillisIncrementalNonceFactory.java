package org.knowm.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

import java.util.concurrent.atomic.AtomicLong;

public class CurrentMillisIncrementalNonceFactory implements SynchronizedValueFactory<Long> {

  private final AtomicLong nonce = new AtomicLong(0);

  @Override
  public Long createValue() {
    return nonce.updateAndGet(prevNonce -> {
      long newNonce = System.currentTimeMillis();

      if (newNonce <= prevNonce) {
        newNonce = prevNonce + 1;
      }
      return newNonce;
    });
  }
}
