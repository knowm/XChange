package org.knowm.xchange.bitmex.service;

import java.io.IOException;

@FunctionalInterface
public interface Supplier<T> {

  /**
   * Gets a result.
   *
   * @return a result
   */
  T get() throws IOException;
}
