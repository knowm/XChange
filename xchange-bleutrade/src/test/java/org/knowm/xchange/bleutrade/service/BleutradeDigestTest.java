package org.knowm.xchange.bleutrade.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import si.mazi.rescu.RestInvocation;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestInvocation.class)
@PowerMockIgnore("javax.crypto.*")
public class BleutradeDigestTest {

  private BleutradeDigest bleutradeDigest;

  @Before
  public void setUp() {
    bleutradeDigest = BleutradeDigest.createInstance("secretKey");
  }

  @Test
  public void shouldEncodeRestInvocation() throws Exception {
    // given
    RestInvocation invocation = mock(RestInvocation.class);
    PowerMockito.when(invocation, "getInvocationUrl").thenReturn("rest body");
    String expected = "6372f349eea659b26f5e01bc76e1485de744a1894d5e036b98eca724a8104719ea8767518286863d1becd0a1313ad5e7e507749f7cdb98a4dee92fec055643c4";

    // when
    String encoded = bleutradeDigest.digestParams(invocation);

    // then
    assertThat(encoded).isEqualTo(expected);
  }

}
