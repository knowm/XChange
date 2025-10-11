package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import si.mazi.rescu.RestInvocation;

public class DaseDigestParamsTest {

  @Test
  public void digestParams_buildsSameAsHelper() {
    String secret =
        "NbMBz+ZLqON9yc/tTA8+5eynWD/37pk6b5yq28q9yH99aJyz4fgifpN1wOv28ReSubHvcsT4Yaq8+c12XjArdg==";
    String method = "GET";
    String pathWithQuery = "/v1/orders?status=open";
    String timestamp = "1719325936838";
    String body = "";

    DaseDigest digest = DaseDigest.createInstance(secret);

    // Expected from helper
    String expected = digest.sign(timestamp, method, pathWithQuery, body);

    // Mock RestInvocation to mirror the above values
    RestInvocation inv = Mockito.mock(RestInvocation.class);
    when(inv.getParamValue(jakarta.ws.rs.HeaderParam.class, "ex-api-timestamp"))
        .thenReturn(timestamp);
    when(inv.getHttpMethod()).thenReturn(method);
    when(inv.getPath()).thenReturn("v1/orders");
    when(inv.getQueryString()).thenReturn("?status=open");
    when(inv.getRequestBody()).thenReturn("");

    String actual = digest.digestParams(inv);
    assertThat(actual).isEqualTo(expected);
  }
}
