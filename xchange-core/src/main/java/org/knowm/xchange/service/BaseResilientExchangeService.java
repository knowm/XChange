package org.knowm.xchange.service;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.client.ResilienceUtils;

/**
 * Abstract class for an "exchange service" which supports resiliency features like retries, rate
 * limiting etc.
 */
public abstract class BaseResilientExchangeService<E extends Exchange>
    extends BaseExchangeService<E> {

  protected final ResilienceRegistries resilienceRegistries;

  protected BaseResilientExchangeService(E exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange);
    this.resilienceRegistries = resilienceRegistries;
  }

  /**
   * Use this method to decorate API calls with resiliency features like retries, rate limiters,
   * etc.
   *
   * @param callable call to exchange API
   * @param <R> type returned by the API call
   * @return builder of a decorated API call
   */
  public <R> ResilienceUtils.DecorateCallableApi<R> decorateApiCall(
      ResilienceUtils.CallableApi<R> callable) {
    return ResilienceUtils.decorateApiCall(
        exchange.getExchangeSpecification().getResilience(), callable);
  }

  /**
   * Returns a managed {@link Retry} or creates a new one with the default Retry configuration from
   * {@link ResilienceRegistries#DEFAULT_RETRY_CONFIG}.
   *
   * @param name the name of the Retry
   * @return The {@link Retry}
   * @see io.github.resilience4j.retry.RetryRegistry#retry(String)
   */
  protected Retry retry(String name) {
    return resilienceRegistries.retries().retry(name);
  }

  /**
   * Returns a managed {@link Retry} or creates a new one. The configuration must have been added
   * upfront in {@link #resilienceRegistries} via {@link ResilienceRegistries#retries()} and the
   * {@link io.github.resilience4j.retry.RetryRegistry#addConfiguration(String, Object)} method. You
   * can also used a predefined retry like {@link
   * ResilienceRegistries#NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME}.
   *
   * @param name the name of the Retry
   * @param configName the name of the shared configuration
   * @return The {@link Retry}
   * @see io.github.resilience4j.retry.RetryRegistry#retry(String, String)
   */
  protected Retry retry(String name, String configName) {
    return resilienceRegistries.retries().retry(name, configName);
  }

  /**
   * Returns a managed {@link RateLimiter} or creates a new one with the default RateLimiter
   * configuration. One main shared rate limiter should be defined for each exchange module via
   * {@link ResilienceRegistries#rateLimiters()} ()} and the {@link
   * io.github.resilience4j.ratelimiter.RateLimiterRegistry#addConfiguration(String, Object)}
   * method.
   *
   * @param name the name of the RateLimiter
   * @return The {@link RateLimiter}
   * @see io.github.resilience4j.ratelimiter.RateLimiterRegistry#rateLimiter(String)
   */
  protected RateLimiter rateLimiter(String name) {
    return resilienceRegistries.rateLimiters().rateLimiter(name);
  }

  /**
   * Returns a managed {@link RateLimiter} or creates a new one. The configuration must have been
   * added upfront {@link #resilienceRegistries} via {@link ResilienceRegistries#rateLimiters()} ()}
   * and the {@link io.github.resilience4j.ratelimiter.RateLimiterRegistry#addConfiguration(String,
   * Object)} method.
   *
   * @param name the name of the RateLimiter
   * @param configName the name of the shared configuration
   * @return The {@link RateLimiter}
   * @see io.github.resilience4j.ratelimiter.RateLimiterRegistry#rateLimiter(String, String)
   */
  protected RateLimiter rateLimiter(String name, String configName) {
    return resilienceRegistries.rateLimiters().rateLimiter(name, configName);
  }
}
