package org.knowm.xchange.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for logging API rate limit warnings consistently across exchanges.
 */
public final class RateLimitLogger {

  private static final Logger LOG = LoggerFactory.getLogger(RateLimitLogger.class);

  private RateLimitLogger() {}

  public static void warn(String exchangeName, int remaining, int limit) {
    if (remaining < limit * 0.1) {
      LOG.warn("[{}] Approaching API rate limit: {} of {} requests left", exchangeName, remaining, limit);
    }
  }
}
