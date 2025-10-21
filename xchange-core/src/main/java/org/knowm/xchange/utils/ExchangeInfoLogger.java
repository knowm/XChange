/*
 * Copyright 2025
 * Utility class for XChange
 */

package org.knowm.xchange.utils;

import org.knowm.xchange.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple helper for logging exchange information.
 * Useful for debugging connection setup or verifying exchange identity.
 */
public final class ExchangeInfoLogger {

  private static final Logger LOG = LoggerFactory.getLogger(ExchangeInfoLogger.class);

  private ExchangeInfoLogger() {
    // Utility class
  }

  public static void printExchangeInfo(Exchange exchange) {
    String name = exchange.getDefaultExchangeSpecification().getExchangeName();
    String uri = exchange.getDefaultExchangeSpecification().getSslUri();
    LOG.info("🔗 Connected to Exchange: {} ({})", name, uri);
  }
}
