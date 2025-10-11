package org.knowm.xchange.dase.service;

import si.mazi.rescu.SynchronizedValueFactory;

/** Supplies current system time in milliseconds as String. */
public class DaseTimestampFactory implements SynchronizedValueFactory<String> {

  @Override
  public String createValue() {
    return String.valueOf(System.currentTimeMillis());
  }
}
