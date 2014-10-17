package com.xeiam.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Default implementation of {@link SynchronizedValueFactory} that returns @{link System#currentTimeMillis}.
 * Note that createValue is normally called in a synchronized block.
 */
public class LongTimeNonceFactory implements SynchronizedValueFactory<Long> {

  public Long createValue() {

    return System.currentTimeMillis();
  }
}
