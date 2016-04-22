package org.knowm.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

public class CurrentTime1000NonceFactory implements SynchronizedValueFactory<Long> {

  @Override
  public Long createValue() {

    return System.currentTimeMillis() / 1000L;
  }
}
