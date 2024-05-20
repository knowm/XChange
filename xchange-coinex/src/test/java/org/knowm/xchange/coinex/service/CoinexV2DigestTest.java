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
    CoinexV2Digest coinexV2Digest = CoinexV2Digest.createInstance("44F7E70A2952C1006E0446044F7757AA60D2F9246D269356");

    when(restInvocation.getHttpMethod()).thenReturn("GET");
    when(restInvocation.getPath()).thenReturn("v2/assets/spot/balance");
    when(restInvocation.getQueryString()).thenReturn("");
    when(restInvocation.getRequestBody()).thenReturn(null);
    Map<String, String> headers = new HashMap<>();
    headers.put("X-COINEX-TIMESTAMP", "1714992192553");
    when(restInvocation.getHttpHeadersFromParams()).thenReturn(headers);

    String actual = coinexV2Digest.digestParams(restInvocation);
    String expected =
        "1e1dd41be7988975295931013c05505a5ed67a591d8e625d10ed137b3fe9592a";

    assertThat(actual).isEqualTo(expected);
  }


}