package org.knowm.xchange.interceptor;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import si.mazi.rescu.Interceptor;

public class InterceptorProvider {

  /** Lazily loaded exactly once, on first access, via the class-initialization holder idiom. */
  private static final class Holder {
    private static final Collection<Interceptor> INTERCEPTORS = load();

    private static Collection<Interceptor> load() {
      final ServiceLoader<Interceptor> serviceLoader = ServiceLoader.load(Interceptor.class);
      return StreamSupport.stream(serviceLoader.spliterator(), false).collect(Collectors.toSet());
    }
  }

  public static Collection<Interceptor> provide() {
    return Holder.INTERCEPTORS;
  }
}
