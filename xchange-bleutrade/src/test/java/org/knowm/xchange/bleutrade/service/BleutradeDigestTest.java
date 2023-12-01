package org.knowm.xchange.bleutrade.service;

import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.RequestWriterResolver;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.RestMethodMetadata;
import si.mazi.rescu.serialization.ToStringRequestWriter;

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
    RequestWriterResolver requestWriterResolver = new RequestWriterResolver();
    requestWriterResolver.addWriter(TEXT_PLAIN, new ToStringRequestWriter());
    RestInvocation invocation =
        RestInvocation.create(
            requestWriterResolver,
            new RestMethodMetadata(
                null,
                null,
                "rest body",
                null,
                null,
                null,
                TEXT_PLAIN,
                TEXT_PLAIN,
                null,
                new HashMap<>(),
                new Annotation[][] {}),
            new Object[] {},
            null);
    String expected =
        "6372f349eea659b26f5e01bc76e1485de744a1894d5e036b98eca724a8104719ea8767518286863d1becd0a1313ad5e7e507749f7cdb98a4dee92fec055643c4";

    // when
    String encoded = bleutradeDigest.digestParams(invocation);

    // then
    assertThat(encoded).isEqualTo(expected);
  }
}
