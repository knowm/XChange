package org.knowm.xchange.binance;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.knowm.xchange.client.ResilienceRegistries;

import java.time.Duration;

public final class BinanceResilience {

  public static final String REQUEST_WEIGHT_RATE_LIMITER = "requestWeight";
  public static final String ORDERS_PER_10_SECONDS_RATE_LIMITER = "ordersPer10Seconds";

  // Spot specified
  public static final String ORDERS_PER_SECOND_RATE_LIMITER = "ordersPerSecond";
  public static final String ORDERS_PER_DAY_RATE_LIMITER = "ordersPerDay";
  public static final String RAW_REQUESTS_RATE_LIMITER = "rawRequests";

  // Futures specified
  public static final String ORDERS_PER_MINUTE_RATE_LIMITER = "ordersPerMINUTE";

  private BinanceResilience() {}

  public static ResilienceRegistries createRegistries() {
    ResilienceRegistries registries = new ResilienceRegistries();
    registries
        .rateLimiters()
        .rateLimiter(
            REQUEST_WEIGHT_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .timeoutDuration(Duration.ofMinutes(1))
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(6000)
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
                      ORDERS_PER_10_SECONDS_RATE_LIMITER,
                      RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                              .limitRefreshPeriod(Duration.ofSeconds(10))
                              .limitForPeriod(100)
                              .build());
      registries
              .rateLimiters()
              .rateLimiter(
                      ORDERS_PER_DAY_RATE_LIMITER,
                      RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                              .limitRefreshPeriod(Duration.ofSeconds(1))
                              .limitForPeriod(200000)
                              .build());
    registries
        .rateLimiters()
        .rateLimiter(
            RAW_REQUESTS_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .timeoutDuration(Duration.ZERO)
                .limitRefreshPeriod(Duration.ofMinutes(5))
                .limitForPeriod(61000)
                .build());
    return registries;
  }

  public static ResilienceRegistries createRegistriesFuture() {
    ResilienceRegistries registries = new ResilienceRegistries();
    registries
        .rateLimiters()
        .rateLimiter(
            REQUEST_WEIGHT_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .timeoutDuration(Duration.ofMinutes(1))
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(2400)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDERS_PER_10_SECONDS_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .limitForPeriod(300)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDERS_PER_MINUTE_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(1200)
                .build());

    // configure SPOT limiters unlimit,for comparability
    registries
        .rateLimiters()
        .rateLimiter(
            ORDERS_PER_SECOND_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(Integer.MAX_VALUE)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            RAW_REQUESTS_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .timeoutDuration(Duration.ZERO)
                .limitRefreshPeriod(Duration.ofMinutes(5))
                .limitForPeriod(Integer.MAX_VALUE)
                .build());
    return registries;
  }
}
