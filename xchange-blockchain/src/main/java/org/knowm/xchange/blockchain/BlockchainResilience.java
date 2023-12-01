package org.knowm.xchange.blockchain;

import static org.knowm.xchange.blockchain.BlockchainConstants.ENDPOINT_RATE_LIMIT;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.client.ResilienceRegistries;

@UtilityClass
public class BlockchainResilience {

  public static ResilienceRegistries getResilienceRegistries() {
    ResilienceRegistries registries = new ResilienceRegistries();
    registries
        .rateLimiters()
        .rateLimiter(
            ENDPOINT_RATE_LIMIT,
            RateLimiterConfig.from(registries.rateLimiters().getDefaultConfig())
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .build());
    return registries;
  }
}
