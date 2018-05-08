package org.knowm.xchange.bitmarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.RestInvocation;

@RunWith(MockitoJUnitRunner.class)
public class BitMarketDigestTest {

  private BitMarketDigest bitMarketDigest;

  @Mock private RestInvocation invocation;

  @Before
  public void setUp() {
    bitMarketDigest = BitMarketDigest.createInstance("secretKey");
  }

  @Test
  public void shouldEncodeRestInvocation() {
    // given
    when(invocation.getRequestBody()).thenReturn("rest body");

    String expected =
        "6372f349eea659b26f5e01bc76e1485de744a1894d5e036b98eca724a8104719ea8767518286863d1becd0a1313ad5e7e507749f7cdb98a4dee92fec055643c4";

    // when
    String encoded = bitMarketDigest.digestParams(invocation);

    // then
    assertEquals(expected, encoded);
  }

  @Test(expected = RuntimeException.class)
  public void shouldFailOnEncodeError() {
    // given
    when(invocation.getRequestBody()).thenThrow(UnsupportedEncodingException.class);

    // when
    bitMarketDigest.digestParams(invocation);

    // then
    fail("BitMarketDigest should throw RuntimeException when encoding caused an error");
  }
}
