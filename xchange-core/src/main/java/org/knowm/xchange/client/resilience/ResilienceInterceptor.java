package org.knowm.xchange.client.resilience;

import com.google.common.annotations.Beta;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.Interceptor;

/**
 * This is an early sample of some functionality we to add to the resilience4j library.
 *
 * <p>In time this will be removed from the Xchange library and become a module in resilience4j
 * repositories.
 *
 * @author walec51
 */
@Beta
public class ResilienceInterceptor implements Interceptor {

  private static final Logger logger = LoggerFactory.getLogger(ResilienceInterceptor.class);

  private final ExchangeSpecification exchangeSpecification;
  private final ResilienceRegistries resilienceRegistries;

  public ResilienceInterceptor(
      ResilienceRegistries resilienceRegistries, ExchangeSpecification exchangeSpecification) {
    this.resilienceRegistries = resilienceRegistries;
    this.exchangeSpecification = exchangeSpecification;
  }

  @Override
  public Object aroundInvoke(InvocationHandler handler, Object proxy, Method method, Object[] args)
      throws Throwable {
    Decorator[] holderAnnotations = method.getAnnotationsByType(Decorator.class);
    if (holderAnnotations == null || holderAnnotations.length == 0) {
      return handler.invoke(proxy, method, args);
    }
    CheckedFunction0<Object> invocation = () -> handler.invoke(proxy, method, args);
    for (Decorator holderAnnotation : holderAnnotations) {
      Retry[] retryAnnotations = holderAnnotation.retry();
      for (Retry retryAnnotation : retryAnnotations) {
        invocation = retryDecoration(retryAnnotation, method, invocation);
      }
      RateLimiter[] rateLimiterAnnotations = holderAnnotation.rateLimiter();
      for (RateLimiter rateLimiterAnnotation : rateLimiterAnnotations) {
        invocation =
            rateLimiterDecoration(rateLimiterAnnotation, handler, proxy, method, args, invocation);
      }
    }
    return invocation.apply();
  }

  private CheckedFunction0<Object> retryDecoration(
      Retry annotation, Method decoratedMethod, CheckedFunction0<Object> invocation) {
    if (!exchangeSpecification.isRetryEnabled()) {
      return invocation;
    }
    String baseConfigName = annotation.baseConfig();
    RetryConfig baseConfig;
    if (baseConfigName.equals(Retry.DEFAULT_CONFIG)) {
      baseConfig = resilienceRegistries.retries().getDefaultConfig();
    } else {
      baseConfig =
          resilienceRegistries
              .retries()
              .getConfiguration(baseConfigName)
              .orElseThrow(
                  () ->
                      new IllegalStateException(
                          "Prototype retry config not found: " + baseConfigName));
    }
    io.github.resilience4j.retry.Retry retry =
        resilienceRegistries.retries().retry(annotation.name(), baseConfig);
    logger.debug(
        "Decorating call to {}#{} with retry {}",
        decoratedMethod.getDeclaringClass().getSimpleName(),
        decoratedMethod.getName(),
        retry.getName());
    return io.github.resilience4j.retry.Retry.decorateCheckedSupplier(retry, invocation);
  }

  private CheckedFunction0<Object> rateLimiterDecoration(
      RateLimiter annotation,
      InvocationHandler handler,
      Object proxy,
      Method decoratedMethod,
      Object[] args,
      CheckedFunction0<Object> invocation) {
    if (!exchangeSpecification.isRateLimiterEnabled()) {
      return invocation;
    }
    io.github.resilience4j.ratelimiter.RateLimiter rateLimiter =
        resilienceRegistries.rateLimiters().rateLimiter(annotation.name());
    int permits = annotation.permits();
    String permitsMethodName = annotation.permitsMethodName();
    if (!permitsMethodName.equals(RateLimiter.FIXED_WEIGHT)) {
      permits = invokePermitsMethod(permitsMethodName, handler, proxy, decoratedMethod, args);
    }
    logger.debug(
        "Decorating call to {}#{} with {} required permits on "
            + "rate limiter {} with {} avaliable permissions",
        decoratedMethod.getDeclaringClass().getSimpleName(),
        decoratedMethod.getName(),
        permits,
        rateLimiter.getName(),
        rateLimiter.getMetrics().getAvailablePermissions());
    return io.github.resilience4j.ratelimiter.RateLimiter.decorateCheckedSupplier(
        rateLimiter, permits, invocation);
  }

  private int invokePermitsMethod(
      String methodName,
      InvocationHandler handler,
      Object proxy,
      Method decoratedMethod,
      Object[] args) {
    try {
      Method permitsMethod =
          decoratedMethod
              .getDeclaringClass()
              .getMethod(methodName, decoratedMethod.getParameterTypes());
      return (int) permitsMethod.invoke(proxy, args);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException(
          "Failed to invoke permits method "
              + methodName
              + ". "
              + "Does it have the same arguments a the decorated method?",
          e);
    } catch (Throwable e) {
      throw new IllegalStateException("Permits method " + methodName + " failed", e);
    }
  }
}
