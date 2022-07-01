package org.knowm.xchange.bithumb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import si.mazi.rescu.RestInvocation;

public class BithumbEndpointGeneratorTest {

  private BithumbEndpointGenerator bithumbEndpointGenerator;

  @Before
  public void init() {
    bithumbEndpointGenerator = new BithumbEndpointGenerator();
  }

  @Test
  public void digestParams() {

    // Given
    final RestInvocation restInvocation = mock(RestInvocation.class);
    when(restInvocation.getPath()).thenReturn("/info/balance");

    // When
    final String param = bithumbEndpointGenerator.digestParams(restInvocation);

    // Then
    assertThat(param).isEqualTo("/info/balance");
  }
}
