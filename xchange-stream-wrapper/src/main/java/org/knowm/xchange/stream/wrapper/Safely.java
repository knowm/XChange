package org.knowm.xchange.stream.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class Safely {

  private Safely() {
    // Not instantiatable
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(Safely.class);

  static boolean run(String gerund, ThrowingRunnable runnable) {
    try {
      runnable.run();
      return true;
    } catch (Exception e) {
      LOGGER.error("Error when {}", gerund, e);
      return false;
    }
  }
}
