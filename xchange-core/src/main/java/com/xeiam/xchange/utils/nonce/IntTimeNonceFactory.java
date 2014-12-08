package com.xeiam.xchange.utils.nonce;

import si.mazi.rescu.SynchronizedValueFactory;

public class IntTimeNonceFactory implements SynchronizedValueFactory<Integer> {
  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  // counter for the nonce
  private static int lastNonce = ((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  @Override
  public Integer createValue() {

    return lastNonce++;
  }
}
