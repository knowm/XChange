package org.knowm.xchange.utils.nonce;

import java.util.concurrent.atomic.AtomicLong;
import si.mazi.rescu.SynchronizedValueFactory;

public class AtomicLongCurrentTimeIncrementalNonceFactory
    implements SynchronizedValueFactory<Long> {

  private final AtomicLong incremental = new AtomicLong(System.currentTimeMillis());

  @Override
  public Long createValue() {

    return incremental.incrementAndGet();
  }
}
