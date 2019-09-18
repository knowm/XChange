package org.knowm.xchange.client.resilience;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Repeatable(ResilienceDecorators.class)
@Documented
public @interface Resilience {

  Retry[] retry() default {};

  RateLimiter[] rateLimiter() default {};

  @interface Retry {

    public static final String DEFAULT_CONFIG = "default";

    public String name();

    public String baseConfig() default DEFAULT_CONFIG;
  }

  @interface RateLimiter {

    static String FIXED_WEIGHT = "__FIXED_WEIGHT__";

    String name();

    int weight() default 1;

    String weightCalculator() default FIXED_WEIGHT;
  }
}
