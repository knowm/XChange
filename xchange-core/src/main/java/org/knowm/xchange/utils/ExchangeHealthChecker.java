/*
 * Copyright (c) 2025 Marta
 * Distributed under the MIT License.
 *
 * Utility class for educational demonstration.
 * This helper allows quick reachability checks of an exchange's API endpoint.
 * Not intended for production usage.
 */

package org.knowm.xchange.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Simple utility to verify that an exchange API endpoint is reachable.
 * Returns HTTP status code or -1 if unreachable.
 */
public final class ExchangeHealthChecker {

  private ExchangeHealthChecker() {}

  /**
   * Performs a HEAD request to the specified endpoint.
   *
   * @param endpoint API base URL (e.g., "https://api.binance.com")
   * @return HTTP status code if reachable, -1 otherwise
   */
  public static int checkEndpoint(String endpoint) {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(endpoint);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("HEAD");
      connection.setConnectTimeout(4000);
      connection.setReadTimeout(4000);
      return connection.getResponseCode();
    } catch (IOException e) {
      return -1;
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public static void main(String[] args) {
    String target = args.length > 0 ? args[0] : "https://api.kraken.com";
    int status = checkEndpoint(target);
    if (status == -1) {
      System.out.println("❌ Unreachable: " + target);
    } else {
      System.out.println("✅ " + target + " responded with HTTP " + status);
    }
  }
}
