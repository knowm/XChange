package org.knowm.xchange.bitget.service;

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
class BitgetDigestTest {

  @Mock RestInvocation restInvocation;

  @Test
  void signature_no_query_params() {
    BitgetDigest bitgetDigest = BitgetDigest.createInstance("secret");

    when(restInvocation.getHttpMethod()).thenReturn("get");
    when(restInvocation.getPath()).thenReturn("api/v2/spot/account/assets");
    when(restInvocation.getQueryString()).thenReturn("");
    when(restInvocation.getRequestBody()).thenReturn(null);
    Map<String, String> headers = new HashMap<>();
    headers.put("ACCESS-TIMESTAMP", "1725040472073");
    when(restInvocation.getHttpHeadersFromParams()).thenReturn(headers);

    String actual = bitgetDigest.digestParams(restInvocation);
    String expected = "p8vWylxL4JJCyy6B81n82ZEySiWChClCw99FTDocviA=";

    assertThat(actual).isEqualTo(expected);
  }
}
