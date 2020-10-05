package org.knowm.xchange.bittrex;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import org.knowm.xchange.client.ResilienceRegistries;

/**
 * Rate limits values provided by the Bittrex team, as of Oct. 1st 2020 public endpoints except
 * order books: 60/min order books: 600/min closed orders: 20/min
 */
public final class BittrexResilience {

  // rest public endpoints except GET /markets/{marketSymbol}/orderbook
  public static final String PUBLIC_ENDPOINTS_RATE_LIMITER = "publicEndpointsRateLimiter";

  // rest endpoint: GET /markets/{marketSymbol}/orderbook
  public static final String GET_ORDER_BOOKS_RATE_LIMITER = "orderBooksRateLimiter";

  // rest endpoint: GET /orders/closed
  public static final String GET_CLOSED_ORDERS_RATE_LIMITER = "closedOrdersRateLimiter";

  private BittrexResilience() {}

  public static ResilienceRegistries createRegistries() {
    ResilienceRegistries registries = new ResilienceRegistries();
    registries
        .rateLimiters()
        .rateLimiter(
            GET_ORDER_BOOKS_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(600)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            GET_CLOSED_ORDERS_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(20)
                .build());
    registries
        .rateLimiters()
        .rateLimiter(
            PUBLIC_ENDPOINTS_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .limitForPeriod(60)
                .build());
    return registries;
  }
}
