package org.knowm.xchange.coinex.service;

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
class CoinexV2DigestTest {

  @Mock
  RestInvocation restInvocation;

  @Test
  void signature() {
    CoinexV2Digest coinexV2Digest = CoinexV2Digest.createInstance("a");

    when(restInvocation.getHttpMethod()).thenReturn("GET");
    when(restInvocation.getPath()).thenReturn("v2/assets/spot/balance");
    when(restInvocation.getQueryString()).thenReturn("");
    when(restInvocation.getRequestBody()).thenReturn(null);
    Map<String, String> headers = new HashMap<>();
    headers.put("X-COINEX-TIMESTAMP", "1714992192553");
    when(restInvocation.getHttpHeadersFromParams()).thenReturn(headers);

    String actual = coinexV2Digest.digestParams(restInvocation);
    String expected = "3d47a904753df7d52fa6c37213bff7db8363249f8f0fed22bf41137805b57a56";

    assertThat(actual).isEqualTo(expected);
  }


}