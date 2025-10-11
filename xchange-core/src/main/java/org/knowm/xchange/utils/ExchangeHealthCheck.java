package org.knowm.xchange.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Small utility that provides a simple HTTP/REST health check for exchange endpoints.
 * Intended to be lightweight and dependency-free (Java 11+ HttpClient).
 *
 * Usage:
 *   boolean ok = ExchangeHealthCheck.isEndpointHealthy("https://api.exchange.com/ping", 2000);
 */
public final class ExchangeHealthCheck {

  private ExchangeHealthCheck() { }

  /**
   * Performs a GET request to the given URL and returns true when the HTTP response
   * status code indicates success (2xx) and the request completes within timeoutMs.
   *
   * This utility is intentionally minimal: it does not parse JSON or handle auth;
   * it's for quick sanity checks in examples or CI smoke-tests.
   *
   * @param url the endpoint to check (including scheme)
   * @param timeoutMs request timeout in milliseconds
   * @return true if endpoint responds with 2xx within timeout; false otherwise
   */
  public static boolean isEndpointHealthy(String url, int timeoutMs) {
    HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofMillis(timeoutMs))
        .build();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(Duration.ofMillis(timeoutMs))
        .GET()
        .build();

    try {
      HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
      int code = response.statusCode();
      return code >= 200 && code < 300;
    } catch (IOException | InterruptedException | IllegalArgumentException e) {
      // InterruptedException: restore interrupt flag
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      return false;
    }
  }
}
