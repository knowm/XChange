package org.knowm.xchange.gateio.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import si.mazi.rescu.RestInvocation;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GateioV4DigestTest {

  @Mock RestInvocation restInvocation;

  @Test
  void signature() {
    GateioV4Digest gateioV4Digest = GateioV4Digest.createInstance("b");

    when(restInvocation.getHttpMethod()).thenReturn("POST");
    when(restInvocation.getPath()).thenReturn("a");
    when(restInvocation.getQueryString()).thenReturn("?b=c");
    when(restInvocation.getRequestBody()).thenReturn("{a:1}");
    Map<String, String> headers = new HashMap<>();
    headers.put("Timestamp", "1691707273890");
    when(restInvocation.getHttpHeadersFromParams()).thenReturn(headers);

    String actual = gateioV4Digest.digestParams(restInvocation);
    String expected =
        "de31e211a60623ba2c41e65a3c21e550400ffccfef55578173e09f2b34cf46b426c587f01b4c12474608dc856b1ba226a71004f7989603236c885c23275d5577";

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void signatureWithNullBody() {
    GateioV4Digest gateioV4Digest = GateioV4Digest.createInstance("b");

    when(restInvocation.getHttpMethod()).thenReturn("POST");
    when(restInvocation.getPath()).thenReturn("a");
    when(restInvocation.getQueryString()).thenReturn("?b=c");
    when(restInvocation.getRequestBody()).thenReturn(null);
    Map<String, String> headers = new HashMap<>();
    headers.put("Timestamp", "1691707273890");
    when(restInvocation.getHttpHeadersFromParams()).thenReturn(headers);

    String actual = gateioV4Digest.digestParams(restInvocation);
    String expected =
        "cc2c75d1db1a1fcf354f7bce55f3126c5a46829449e8f6c087b785d71b12aa142fee8fb9750f60a2cdea4fd84f8e7f1835a21a47d6709cc92b4ff757f7ccdbd1";

    assertThat(actual).isEqualTo(expected);
  }
}
