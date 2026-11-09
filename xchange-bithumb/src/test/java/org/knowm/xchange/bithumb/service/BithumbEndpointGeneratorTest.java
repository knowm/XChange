package org.knowm.xchange.bithumb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import si.mazi.rescu.RestInvocation;

class BithumbEndpointGeneratorTest {

  private BithumbEndpointGenerator bithumbEndpointGenerator;

  @BeforeEach
  void init() {
    bithumbEndpointGenerator = new BithumbEndpointGenerator();
  }

  @Test
  void digestParams() {

    // Given
    final RestInvocation restInvocation = mock(RestInvocation.class);
    when(restInvocation.getPath()).thenReturn("/info/balance");

    // When
    final String param = bithumbEndpointGenerator.digestParams(restInvocation);

    // Then
    assertThat(param).isEqualTo("/info/balance");
  }
}
