/**
 * RateLimitLogger.java
 * Utility class to log API rate-limit warnings consistently across all exchange modules.
 *
 * Author: Marta Nowak
 * Date: 2025-11-01
 */

package org.knowm.xchange.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateLimitLogger {

  private static final Logger log = LoggerFactory.getLogger(RateLimitLogger.class);
  private static long lastWarningTime = 0L;
  private static final long WARNING_INTERVAL_MS = 30_000; // 30s

  private RateLimitLogger() {}

  /**
   * Logs a rate-limit warning message if not logged recently.
   *
   * @param exchangeName Exchange identifier
   * @param message Optional message
   */
  public static void warn(String exchangeName, String message) {
    long now = System.currentTimeMillis();
    if (now - lastWarningTime > WARNING_INTERVAL_MS) {
      log.warn("[{}] API rate limit approaching. {}", exchangeName, message);
      lastWarningTime = now;
    }
  }
}
