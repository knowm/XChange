package org.knowm.xchange.livecoin;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import org.knowm.xchange.client.ResilienceRegistries;

public final class LivecoinResilience {

  public static final String MAIN_RATE_LIMITER = "main";

  private LivecoinResilience() {}

  public static ResilienceRegistries createRegistries() {
    ResilienceRegistries registries = new ResilienceRegistries();
    registries
        .rateLimiters()
        .rateLimiter(
            MAIN_RATE_LIMITER,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(80))
                .limitForPeriod(40)
                .build());
    return registries;
  }
}
