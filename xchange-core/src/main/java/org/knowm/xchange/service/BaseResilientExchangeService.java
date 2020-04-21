package org.knowm.xchange.service;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.client.ResilienceUtils;

/**
 * Top of the hierarchy abstract class for an "exchange service" which supports resiliency features
 * like retries, rate limiting etc.
 */
public abstract class BaseResilientExchangeService<E extends Exchange> extends BaseExchangeService {

  protected final ResilienceRegistries resilienceRegistries;

  protected BaseResilientExchangeService(E exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange);
    this.resilienceRegistries = resilienceRegistries;
  }

  public <R> ResilienceUtils.DecorateCallableApi<R> decorateApiCall(
      ResilienceUtils.CallableApi<R> callable) {
    return ResilienceUtils.decorateApiCall(
        exchange.getExchangeSpecification().getResilience(), callable);
  }

  /** @see io.github.resilience4j.retry.RetryRegistry#retry(String) */
  protected Retry retry(String name) {
    return resilienceRegistries.retries().retry(name);
  }

  /** @see io.github.resilience4j.retry.RetryRegistry#retry(String, String) */
  protected Retry retry(String name, String configName) {
    return resilienceRegistries.retries().retry(name, configName);
  }

  /** @see io.github.resilience4j.ratelimiter.RateLimiterRegistry#rateLimiter(String) */
  protected RateLimiter rateLimiter(String name) {
    return resilienceRegistries.rateLimiters().rateLimiter(name);
  }

  /** @see io.github.resilience4j.ratelimiter.RateLimiterRegistry#rateLimiter(String, String) */
  protected RateLimiter rateLimiter(String name, String configName) {
    return resilienceRegistries.rateLimiters().rateLimiter(name, configName);
  }
}
