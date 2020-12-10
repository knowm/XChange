package org.knowm.xchange.coinbasepro;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.knowm.xchange.client.ResilienceRegistries;

import java.time.Duration;

public class CoinbaseProResilience {

    public static final String ORDERS_PER_DAY_RATE_LIMITER = "ordersPerDay";

    public static final String PRIVATE_PER_SECOND_RATE_LIMITER = "privateOrdersPerSecond";

    public static final String PUBLIC_PER_SECOND_RATE_LIMITER = "publicOrdersPerSecond";

    private CoinbaseProResilience() {
    }

    public static ResilienceRegistries createRegistries() {
        ResilienceRegistries registries = new ResilienceRegistries();
        registries
                .rateLimiters()
                .rateLimiter(
                        PRIVATE_PER_SECOND_RATE_LIMITER,
                        RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                                .limitRefreshPeriod(Duration.ofSeconds(1))
                                .limitForPeriod(5)
                                .build());
        registries
                .rateLimiters()
                .rateLimiter(
                        PUBLIC_PER_SECOND_RATE_LIMITER,
                        RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                                .limitRefreshPeriod(Duration.ofSeconds(1))
                                .limitForPeriod(3)
                                .build());
        registries
                .rateLimiters()
                .rateLimiter(
                        ORDERS_PER_DAY_RATE_LIMITER,
                        RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                                .timeoutDuration(Duration.ZERO)
                                .limitRefreshPeriod(Duration.ofDays(1))
                                .limitForPeriod(500)
                                .build());
        return registries;
    }
}
