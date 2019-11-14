package org.knowm.xchange.client.resilience;

import com.google.common.annotations.Beta;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an early sample of some functionality we to add to the resilience4j library.
 *
 * <p>In time this will be removed from the Xchange library and we will refactor our code to use the
 * annotations from the resilience4j library.
 *
 * @author walec51
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
@Beta
public @interface Decorators {

  public Decorator[] value();
}
