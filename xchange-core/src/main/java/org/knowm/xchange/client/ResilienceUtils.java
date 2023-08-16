package org.knowm.xchange.client;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Either;
import java.io.IOException;
import java.util.concurrent.Callable;
import jakarta.ws.rs.core.Response;
import org.knowm.xchange.ExchangeSpecification;
import si.mazi.rescu.HttpStatusExceptionSupport;

public final class ResilienceUtils {

  private ResilienceUtils() {}

  public static <T> DecorateCallableApi<T> decorateApiCall(
      ExchangeSpecification.ResilienceSpecification resilienceSpecification,
      CallableApi<T> callable) {
    return new DecorateCallableApi<>(resilienceSpecification, callable);
  }

  /** Function which can be used check if a particular HTTP status code was returned */
  public static boolean matchesHttpCode(
      final Either<? extends Throwable, ?> e, final Response.Status status) {
    if (e.isRight()) {
      return false;
    }
    final Throwable throwable = e.getLeft();
    return throwable instanceof HttpStatusExceptionSupport
        && ((HttpStatusExceptionSupport) throwable).getHttpStatusCode() == status.getStatusCode();
  }

  public interface CallableApi<T> extends Callable<T> {

    T call() throws IOException;

    static <T> CallableApi<T> wrapCallable(Callable<T> callable) {
      return () -> {
        try {
          return callable.call();
        } catch (IOException | RuntimeException e) {
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