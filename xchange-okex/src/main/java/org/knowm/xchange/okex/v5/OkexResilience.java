package org.knowm.xchange.okex.v5;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.client.ResilienceUtils;

import java.time.Duration;

import static javax.ws.rs.core.Response.Status.TOO_MANY_REQUESTS;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexResilience {
  public static ResilienceRegistries createRegistries() {
    final ResilienceRegistries registries = new ResilienceRegistries();

    Okex.publicPathRateLimits.forEach(
        (path, limit) -> {
          registries
              .rateLimiters()
              .rateLimiter(
                  path,
                  RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                      .limitRefreshPeriod(Duration.ofSeconds(limit.get(1)))
                      .limitForPeriod(limit.get(0))
                      .drainPermissionsOnResult(
                          e -> ResilienceUtils.matchesHttpCode(e, TOO_MANY_REQUESTS))
                      .build());
        });

    OkexAuthenticated.privatePathRateLimits.forEach(
        (path, limit) -> {
          registries
              .rateLimiters()
              .rateLimiter(
                  path,
                  RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                      .limitRefreshPeriod(Duration.ofSeconds(limit.get(1)))
                      .limitForPeriod(limit.get(0))
                      .drainPermissionsOnResult(
                          e -> ResilienceUtils.matchesHttpCode(e, TOO_MANY_REQUESTS))
                      .build());
        });

    return registries;
  }
}
