package org.knowm.xchange.client.resilience;

import static io.github.resilience4j.ratelimiter.RateLimiter.waitForPermission;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.vavr.CheckedFunction0;
import java.util.stream.IntStream;

public interface WeighedRateLimiter {

  static <T> CheckedFunction0<T> decorateCheckedSupplier(
      RateLimiter rateLimiter, int weight, CheckedFunction0<T> supplier) {
    return () -> {
      waitForPermission(rateLimiter);
      IntStream.range(1, weight).forEach(i -> rateLimiter.reservePermission());
      return supplier.apply();
    };
  }
}
