package org.knowm.xchange.client.resilience;

import com.google.common.annotations.Beta;

/**
 * This is an early sample of some functionality we to add to the resilience4j library.
 *
 * <p>In time this will be removed from the Xchange library and we will refactor our code to use the
 * annotations from the resilience4j library.
 *
 * @author walec51
 */
@Beta
public @interface Retry {

  public static final String DEFAULT_CONFIG = "default";

  public String name();

  public String baseConfig() default DEFAULT_CONFIG;
}
