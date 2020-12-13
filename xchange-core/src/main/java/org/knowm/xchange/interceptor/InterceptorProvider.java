package org.knowm.xchange.interceptor;

import com.google.common.base.Suppliers;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import si.mazi.rescu.Interceptor;

public class InterceptorProvider {

  private static final Supplier<Collection<Interceptor>> INTERCEPTORS_SUPPLIER =
      Suppliers.memoize(
          () -> {
            final ServiceLoader<Interceptor> serviceLoader = ServiceLoader.load(Interceptor.class);
            return StreamSupport.stream(serviceLoader.spliterator(), false)
                .collect(Collectors.toSet());
          });

  public static Collection<Interceptor> provide() {
    return INTERCEPTORS_SUPPLIER.get();
  }
}
