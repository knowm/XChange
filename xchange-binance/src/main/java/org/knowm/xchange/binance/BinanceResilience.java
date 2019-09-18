package org.knowm.xchange.binance;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import org.knowm.xchange.client.resilience.ResilienceRegistries;

public final class BinanceResilience {

  /**
   * Name of main rate limiter
   *
   * <p>1 permission = 1 weight, so it can be used out of the box for calls with weight = 1
   */
  public static final String REQUEST_WEIGHT_RATE_LIMITER = "requestWeight";

  public static final String ORDERS_PER_SECOND_RATE_LIMITER = "ordersPerSecond";

  public static final String ORDERS_PER_DAY_RATE_LIMITER = "ordersPerDay";

  private BinanceResilience() {}

  public static ResilienceRegistries createRegistries() {
    ResilienceRegistries registries = new ResilienceRegistries();
    registries
        .rateLimiters()
        .rateLimiter(
            REQUEST_WEIGHT_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(1200)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDERS_PER_SECOND_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDERS_PER_DAY_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofDays(1))
                .limitForPeriod(200000)
                .build());
    return registries;
  }
}
