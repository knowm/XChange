package org.knowm.xchange.coinsph;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import si.mazi.rescu.HttpStatusIOException;

public final class CoinsphResilience {

  // Define rate limiter names based on Coins.ph API documentation
  // Example: REQUEST_WEIGHT for general requests, ORDERS for order placement/cancellation
  public static final String REQUEST_WEIGHT_RATE_LIMITER = "requestWeight";
  public static final String ORDERS_RATE_LIMITER = "orders";

  // Add more as needed, e.g., for different types of endpoints or specific limits

  private CoinsphResilience() {}

  public static ResilienceRegistries createRegistries() {
    final ResilienceRegistries registries = new ResilienceRegistries();

    // Configure Request Weight Rate Limiter
    // From docs: "The combined weight of all requests using an API key cannot exceed 1200 per
    // minute."
    // This is a general limit. Specific endpoints might have their own.
    // For simplicity, we'll start with one main request weight limiter.
    // Individual calls will specify their "weight".
    registries
        .rateLimiters()
        .rateLimiter(
            REQUEST_WEIGHT_RATE_LIMITER,
            RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(1200) // Max 1200 "weight" per minute
                .build());

    // Configure Orders Rate Limiter
    // From docs: "Orders have a separate rate limit of 50 per 10 seconds and 160,000 per 24 hours."
    // We'll implement the 50 per 10 seconds limit here. The 24-hour limit is harder to enforce
    // strictly at client-side and is usually a broader account/IP limit.
    registries
        .rateLimiters()
        .rateLimiter(
            ORDERS_RATE_LIMITER,
            RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .limitForPeriod(50) // Max 50 orders per 10 seconds
                .build());

    // Configure Retry Strategy
    // Retry on specific Coins.ph exceptions or HTTP status codes indicating temporary issues.
    // Example: Retry on 429 (Too Many Requests) if not handled by RateLimiter, or 5xx server
    // errors.
    registries
        .retries()
        .retry(
            "generalRetry", // A name for this retry configuration
            io.github.resilience4j.retry.RetryConfig.custom()
                .maxAttempts(3) // Number of attempts
                .waitDuration(Duration.ofMillis(500)) // Wait duration between attempts
                .retryExceptions(
                    HttpStatusIOException.class, // General network issues
                    CoinsphException.class // Specific API errors that might be retryable
                    )
                // .ignoreExceptions() // Exceptions that should not be retried
                .build());

    // Add more specific rate limiters or retry strategies if needed for different API endpoints.

    return registries;
  }
}
