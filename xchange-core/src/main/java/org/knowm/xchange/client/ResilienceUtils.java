package org.knowm.xchange.client;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.knowm.xchange.ExchangeSpecification;

public final class ResilienceUtils {

  private ResilienceUtils() {}

  public static <T> DecorateCallableApi<T> decorateApiCall(
      ExchangeSpecification.ResilienceSpecification resilienceSpecification,
      CallableApi<T> callable) {
    return new DecorateCallableApi<>(resilienceSpecification, callable);
  }

  public interface CallableApi<T> extends Callable<T> {

    T call() throws IOException;

    static <T> CallableApi<T> wrapCallable(Callable<T> callable) {
      return () -> {
        try {
          return callable.call();
        } catch (IOException e) {
          throw e;
        } catch (RuntimeException e) {
          throw e;
        } catch (Throwable e) {
          throw new IllegalStateException(e);
        }
      };
    }
  }

  public static class DecorateCallableApi<T> {
    private final ExchangeSpecification.ResilienceSpecification resilienceSpecification;
    private CallableApi<T> callable;

    private DecorateCallableApi(
        ExchangeSpecification.ResilienceSpecification resilienceSpecification,
        CallableApi<T> callable) {
      this.resilienceSpecification = resilienceSpecification;
      this.callable = callable;
    }

    public DecorateCallableApi<T> withRetry(Retry retryContext) {
      if (resilienceSpecification.isRetryEnabled()) {
        this.callable =
            CallableApi.wrapCallable(Retry.decorateCallable(retryContext, this.callable));
      }
      return this;
    }

    public DecorateCallableApi<T> withRateLimiter(RateLimiter rateLimiter) {
      if (resilienceSpecification.isRateLimiterEnabled()) {
        return this.withRateLimiter(rateLimiter, 1);
      }
      return this;
    }

    public DecorateCallableApi<T> withRateLimiter(RateLimiter rateLimiter, int permits) {
      if (resilienceSpecification.isRateLimiterEnabled()) {
        this.callable =
            CallableApi.wrapCallable(
                RateLimiter.decorateCallable(rateLimiter, permits, this.callable));
      }
      return this;
    }

    public T call() throws IOException {
      return this.callable.call();
    }
  }
}
