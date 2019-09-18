package org.knowm.xchange.client.resilience;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.knowm.xchange.ExchangeSpecification;
import si.mazi.rescu.Interceptor;

public class ResilienceInterceptor implements Interceptor {

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
    Resilience[] holderAnnotations = method.getAnnotationsByType(Resilience.class);
    if (holderAnnotations == null || holderAnnotations.length == 0) {
      return handler.invoke(proxy, method, args);
    }
    CheckedFunction0<Object> invocation = () -> handler.invoke(proxy, method, args);
    for (Resilience holderAnnotation : holderAnnotations) {
      Resilience.Retry[] retryAnnotations = holderAnnotation.retry();
      for (Resilience.Retry retryAnnotation : retryAnnotations) {
        invocation = retryDecoration(retryAnnotation, invocation);
      }
      Resilience.RateLimiter[] rateLimiterAnnotations = holderAnnotation.rateLimiter();
      for (Resilience.RateLimiter rateLimiterAnnotation : rateLimiterAnnotations) {
        invocation =
            rateLimiterDecoration(rateLimiterAnnotation, handler, proxy, method, args, invocation);
      }
    }
    return invocation.apply();
  }

  private CheckedFunction0<Object> retryDecoration(
      Resilience.Retry annotation, CheckedFunction0<Object> invocation) {
    if (!exchangeSpecification.isRetryEnabled()) {
      return invocation;
    }
    String baseConfigName = annotation.baseConfig();
    RetryConfig baseConfig;
    if (baseConfigName.equals(Resilience.Retry.DEFAULT_CONFIG)) {
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
    Retry retry = resilienceRegistries.retries().retry(annotation.name(), baseConfig);
    return Retry.decorateCheckedSupplier(retry, invocation);
  }

  private CheckedFunction0<Object> rateLimiterDecoration(
      Resilience.RateLimiter annotation,
      InvocationHandler handler,
      Object proxy,
      Method decoratedMethod,
      Object[] args,
      CheckedFunction0<Object> invocation) {
    if (!exchangeSpecification.isRateLimiterEnabled()) {
      return invocation;
    }
    RateLimiter rateLimiter = resilienceRegistries.rateLimiters().rateLimiter(annotation.name());
    // TODO: add better weight support to resilience4j
    int weight = annotation.weight();
    String weightCalculatorsName = annotation.weightCalculator();
    if (!weightCalculatorsName.equals(Resilience.RateLimiter.FIXED_WEIGHT)) {
      weight = invokeWeightCalculator(weightCalculatorsName, handler, proxy, decoratedMethod, args);
    }
    return WeighedRateLimiter.decorateCheckedSupplier(rateLimiter, weight, invocation);
  }

  private int invokeWeightCalculator(
      String methodName,
      InvocationHandler handler,
      Object proxy,
      Method decoratedMethod,
      Object[] args) {
    try {
      Method weightCalculator =
          decoratedMethod
              .getDeclaringClass()
              .getMethod(methodName, decoratedMethod.getParameterTypes());
      return (int) weightCalculator.invoke(proxy, args);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException(
          "Failed to invoke weight calculator method "
              + methodName
              + ". "
              + "Does it have the same arguments a the decorated method?",
          e);
    } catch (Throwable e) {
      throw new IllegalStateException("Weight calculator method " + methodName + " failed", e);
    }
  }
}
