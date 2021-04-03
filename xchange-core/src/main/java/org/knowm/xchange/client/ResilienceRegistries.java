package org.knowm.xchange.client;

import com.google.common.annotations.Beta;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Duration;
import org.knowm.xchange.exceptions.ExchangeUnavailableException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.exceptions.OperationTimeoutException;

@Beta
public class ResilienceRegistries {

  public static final RetryConfig DEFAULT_RETRY_CONFIG =
      RetryConfig.custom()
          .maxAttempts(3)
          .intervalFunction(IntervalFunction.ofExponentialBackoff(Duration.ofMillis(50), 4))
          .retryExceptions(
              IOException.class,
              ExchangeUnavailableException.class,
              InternalServerException.class,
              OperationTimeoutException.class)
          .build();

  public static final String NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME = "nonIdempotentCallsBase";

  /**
   * Suggested for calls that are not idempotent like placing order or withdrawing funds
   *
   * <p>Well designed exchange APIs will have mechanisms that make even placing orders idempotent.
   * Most however cannot handle retries on this type of calls and if you do one after a socket read
   * timeout for eq. then this may result in placing two identical orders instead of one. For such
   * exchanged this retry configuration is recommended.
   */
  public static final RetryConfig DEFAULT_NON_IDEMPOTENT_CALLS_RETRY_CONFIG =
      RetryConfig.from(DEFAULT_RETRY_CONFIG)
          .retryExceptions(
              UnknownHostException.class, SocketException.class, ExchangeUnavailableException.class)
          .build();

  public static final RateLimiterConfig DEFAULT_GLOBAL_RATE_LIMITER_CONFIG =
      RateLimiterConfig.custom()
          .timeoutDuration(Duration.ofSeconds(30))
          .limitRefreshPeriod(Duration.ofMinutes(1))
          .limitForPeriod(1200)
          .build();

  private final RetryRegistry retryRegistry;

  private final RateLimiterRegistry rateLimiterRegistry;

  public ResilienceRegistries() {
    this(DEFAULT_RETRY_CONFIG, DEFAULT_NON_IDEMPOTENT_CALLS_RETRY_CONFIG);
  }

  public ResilienceRegistries(
      RetryConfig globalRetryConfig, RetryConfig nonIdempotentCallsRetryConfig) {
    this(globalRetryConfig, nonIdempotentCallsRetryConfig, DEFAULT_GLOBAL_RATE_LIMITER_CONFIG);
  }

  public ResilienceRegistries(
      RetryConfig globalRetryConfig,
      RetryConfig nonIdempotentCallsRetryConfig,
      RateLimiterConfig globalRateLimiterConfig) {
    this(
        retryRegistryOf(globalRetryConfig, nonIdempotentCallsRetryConfig),
        RateLimiterRegistry.of(globalRateLimiterConfig));
  }

  public ResilienceRegistries(
      RetryRegistry retryRegistry, RateLimiterRegistry rateLimiterRegistry) {
    this.retryRegistry = retryRegistry;
    this.rateLimiterRegistry = rateLimiterRegistry;
  }

  public RetryRegistry retries() {
    return retryRegistry;
  }

  public RateLimiterRegistry rateLimiters() {
    return rateLimiterRegistry;
  }

  private static RetryRegistry retryRegistryOf(
      RetryConfig globalRetryConfig, RetryConfig nonIdempotentCallsRetryConfig) {
    RetryRegistry registry = RetryRegistry.of(globalRetryConfig);
    registry.addConfiguration(
        NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME, nonIdempotentCallsRetryConfig);
    return registry;
  }
}
