package com.xeiam.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

public class CurrentTimeNonceFactory implements SynchronizedValueFactory<Long> {

  @Override
  public Long createValue() {

    return System.currentTimeMillis();
  }
}
