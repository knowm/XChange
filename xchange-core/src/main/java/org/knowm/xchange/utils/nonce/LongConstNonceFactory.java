package org.knowm.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

/** Trivial {@link SynchronizedValueFactory} implementation for testing */
public class LongConstNonceFactory implements SynchronizedValueFactory<Long> {
  public static final SynchronizedValueFactory<Long> NONCE_FACTORY = new LongConstNonceFactory();

  @Override
  public Long createValue() {
    return 0L;
  }
}
