package org.knowm.xchange.interceptor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import si.mazi.rescu.Interceptor;

class InterceptorProviderTest {

  @Test
  void resolveInterceptors() {
    final Collection<Interceptor> resolvedInterceptors = InterceptorProvider.provide();

    assertThat(resolvedInterceptors).hasSize(1);
    assertThat(resolvedInterceptors.iterator().next()).isInstanceOf(TestInterceptor.class);
  }
}
