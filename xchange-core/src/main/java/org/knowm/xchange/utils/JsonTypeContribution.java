package org.knowm.xchange.utils;

import java.util.Collection;
import java.util.ServiceLoader;

/**
 * Maps a base type (usually an interface) to a collection of implementations
 * for {@link ServiceLoader} injection.  See {@link AbstractServiceLoaderTypeResolver}
 * for more information.
 */
public interface JsonTypeContribution<T> {

  /**
   * @return The base type.
   */
  Class<T> baseType();

  /**
   * @return The implementations of {@link #baseType()} to be injected at runtime.
   */
  Collection<Class<? extends T>> subTypes();

}
