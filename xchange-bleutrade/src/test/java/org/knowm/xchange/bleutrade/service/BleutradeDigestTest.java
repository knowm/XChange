package org.knowm.xchange.bleutrade.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.RestInvocation;

@RunWith(MockitoJUnitRunner.class)
public class BleutradeDigestTest {

  private BleutradeDigest bleutradeDigest;

  @Before
  public void setUp() {
    bleutradeDigest = BleutradeDigest.createInstance("secretKey");
  }

  @Test
  public void shouldEncodeRestInvocation() throws Exception {
    // given
    RestInvocation invocation =
        new RestInvocation(null, null, null, null, "rest body", null, null, null);
    String expected =
        "6372f349eea659b26f5e01bc76e1485de744a1894d5e036b98eca724a8104719ea8767518286863d1becd0a1313ad5e7e507749f7cdb98a4dee92fec055643c4";

    // when
    String encoded = bleutradeDigest.digestParams(invocation);

    // then
    assertThat(encoded).isEqualTo(expected);
  }
}
