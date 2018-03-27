package org.knowm.xchange.utils.nonce;

import java.util.concurrent.atomic.AtomicLong;
import si.mazi.rescu.SynchronizedValueFactory;

public class AtomicLongIncrementalTime2013NonceFactory implements SynchronizedValueFactory<Long> {

  private static final long START_MILLIS =
      1356998400000L; // Jan 1st, 2013 in milliseconds from epoch

  // counter for the nonce
  private final AtomicLong lastNonce =
      new AtomicLong((System.currentTimeMillis() - START_MILLIS) / 250L);

  @Override
  public Long createValue() {

    return lastNonce.incrementAndGet();
  }
}
