package org.knowm.xchange.bithumb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.FormParam;
import org.junit.Before;
import org.junit.Test;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class BithumbDigestTest {

  private BithumbDigest bithumbDigest;

  @Before
  public void init() {
    bithumbDigest = BithumbDigest.createInstance("secretKey");
  }

  @Test
  public void testDigestParams() {

    // given
    final Map<String, String> map = new HashMap<>();
    map.put("Api-Nonce", "1547303756000");

    final Map<Class<? extends Annotation>, Params> formParams = new LinkedHashMap<>();
    formParams.put(FormParam.class, Params.of("endpoint", "/info/balance", "currency", "ALL"));

    final RestInvocation restInvocation = mock(RestInvocation.class);
    when(restInvocation.getPath()).thenReturn("/info/balance");
    when(restInvocation.getParamsMap()).thenReturn(formParams);
    when(restInvocation.getHttpHeadersFromParams()).thenReturn(map);

    // when
    final String sign = bithumbDigest.digestParams(restInvocation);

    // then
    assertThat(sign)
        .isEqualTo(
            "MTkzMGVmY2QzZGUzZWQ1ZmRkZmVhMGFhMmU2MjNkMThlYmQ2NTkyOTU1ZTcxMTc2N2NjZTY0ODdhOTJmOWRhMTNjZTYxMWVlYTJlOTEzOTBlYzlmMDU1MDVkMTk5OGY3YjUyMDY1NzVhZThlYWUxMmJmNjIxNjlkNTY2YmM0Nzc=");
  }
}
