package org.knowm.xchange.gateio;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.knowm.xchange.client.ResilienceRegistries;

import java.time.Duration;

public class GateioResilience {
  public static final String ORDERS_RATE_LIMITER = "ordersPerSecond";
  public static final String DYNAMIC_TRADING_FEE_RATE_LIMITER = "dynamicTradingFee";
  public static final String LEVERAGE_RATE_LIMITER = "setLeverage";
  public static final String TICKERS_RATE_LIMITER = "tickers";
  public static final String FUNDING_HISTORY_RATE_LIMITER = "fundingHistory";
  public static final String CANDLESTICK_DATA_RATE_LIMITER = "candlestickData";

  public static ResilienceRegistries createRegistries(boolean isFutures) {
    ResilienceRegistries registries = new ResilienceRegistries();
    registries
        .rateLimiters()
        .rateLimiter(
            DYNAMIC_TRADING_FEE_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .limitForPeriod(200)
                .timeoutDuration(Duration.ofSeconds(0))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            LEVERAGE_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .timeoutDuration(Duration.ofSeconds(0))
                .limitForPeriod(200)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            FUNDING_HISTORY_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .limitForPeriod(200)
                .timeoutDuration(Duration.ofSeconds(0))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            CANDLESTICK_DATA_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .timeoutDuration(Duration.ofSeconds(0))
                .limitForPeriod(200)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            TICKERS_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(10))
                .timeoutDuration(Duration.ofSeconds(0))
                .limitForPeriod(200)
                .build());
    if (isFutures)
      registries
          .rateLimiters()
          .rateLimiter(
              ORDERS_RATE_LIMITER,
              RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                  .limitRefreshPeriod(Duration.ofSeconds(1))
                  .timeoutDuration(Duration.ofSeconds(0))
                  .limitForPeriod(100)
                  .build());
    else
      registries
          .rateLimiters()
          .rateLimiter(
              ORDERS_RATE_LIMITER,
              RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                  .limitRefreshPeriod(Duration.ofSeconds(1))
                  .timeoutDuration(Duration.ofSeconds(0))
                  .limitForPeriod(10)
                  .build());

    return registries;
  }
}
