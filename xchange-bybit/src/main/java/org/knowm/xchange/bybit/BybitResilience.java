package org.knowm.xchange.bybit;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;
import static jakarta.ws.rs.core.Response.Status.TOO_MANY_REQUESTS;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.client.ResilienceUtils;

public class BybitResilience {

  /**
   * for UTA 2.0 pro account
   * <a href="https://bybit-exchange.github.io/docs/v5/rate-limit">Documentation</a>
   */
  public static final String GLOBAL_RATE_LIMITER = "global";

  // /v5/order/create
  public static final String ORDER_CREATE_LINEAR_AND_INVERSE_RATE_LIMITER = "orderCreateLinearAndInverse";
  public static final String ORDER_CREATE_SPOT_RATE_LIMITER = "orderCreateSpot";
  public static final String ORDER_CREATE_OPTION_LIMITER = "orderCreateOption";

  // /v5/order/amend
  public static final String ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER = "orderAmendLinearAndInverse";
  public static final String ORDER_AMEND_SPOT_RATE_LIMITER = "orderAmendSpot";
  public static final String ORDER_AMEND_OPTION_LIMITER = "orderAmendOption";

  // /v5/order/cancel
  public static final String ORDER_CANCEL_LINEAR_AND_INVERSE_RATE_LIMITER = "orderCancelLinearAndInverse";
  public static final String ORDER_CANCEL_SPOT_RATE_LIMITER = "orderCancelSpot";
  public static final String ORDER_CANCEL_OPTION_LIMITER = "orderCancelOption";

  // /v5/position/set-leverage
  public static final String POSITION_SET_LEVERAGE_INVERSE_RATE_LIMITER = "positionSetLeverageInverse";
  public static final String POSITION_SET_LEVERAGE_LINEAR_RATE_LIMITER = "positionSetLeverageLinear";


  public static ResilienceRegistries createRegistries() {
    ResilienceRegistries registries = new ResilienceRegistries();

    registries
        .rateLimiters()
        .rateLimiter(
            GLOBAL_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(5))
                .limitForPeriod(600)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());

    // /order/create
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_CREATE_LINEAR_AND_INVERSE_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_CREATE_SPOT_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(20)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_CREATE_OPTION_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());

    // /order/amend
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_AMEND_SPOT_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_AMEND_OPTION_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());

    // /order/cancel
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_CANCEL_LINEAR_AND_INVERSE_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_CANCEL_SPOT_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(20)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            ORDER_CANCEL_OPTION_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());

    // /position/set-leverage
    registries
        .rateLimiters()
        .rateLimiter(
            POSITION_SET_LEVERAGE_INVERSE_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            POSITION_SET_LEVERAGE_LINEAR_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(1))
                .build());
    return registries;
  }

}
