package org.knowm.xchange.bybit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BybitTimeStampFactory implements SynchronizedValueFactory<Long> {
  private static final Logger LOG = LoggerFactory.getLogger(BybitTimeStampFactory.class);

  @Override
  public Long createValue() {
    return System.currentTimeMillis();
  }
}
