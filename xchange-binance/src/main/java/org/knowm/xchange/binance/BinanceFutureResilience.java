package org.knowm.xchange.binance;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import org.knowm.xchange.client.ResilienceRegistries;

public class BinanceFutureResilience {

  public static final String REQUEST_WEIGHT_RATE_LIMITER = "requestWeight";

  public static final String ORDERS_PER_10_SECONDS_RATE_LIMITER = "ordersPer10Seconds";

  public static final String ORDERS_PER_MINUTE_RATE_LIMITER = "ordersPerMinute";

  private BinanceFutureResilience() {}

  public static ResilienceRegistries createRegistries() {
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
                .timeoutDuration(Duration.ZERO)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(1200)
                .build());
    return registries;
  }
}
