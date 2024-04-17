package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import si.mazi.rescu.RestInvocation;

@ExtendWith(MockitoExtension.class)
class GateioV4DigestTest {

  @Mock
  RestInvocation restInvocation;

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
    String expected = "de31e211a60623ba2c41e65a3c21e550400ffccfef55578173e09f2b34cf46b426c587f01b4c12474608dc856b1ba226a71004f7989603236c885c23275d5577";

    assertThat(actual).isEqualTo(expected);
  }

}