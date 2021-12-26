package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

// import static org.powermock.api.mockito.PowerMockito.mock;
// import org.mockito.Mockito;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PowerMockIgnore;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;

// @RunWith(PowerMockRunner.class)
// @PrepareForTest(RestInvocation.class)
// @PowerMockIgnore("javax.crypto.*")
public class BTCMarketsDigestTest {

  private BTCMarketsDigest btcMarketsDigest;

  @Before
  public void setUp() {
    btcMarketsDigest = new BTCMarketsDigest("c2VjcmV0S2V5", true); // encoded 'secretKey'
  }

  @Test
  public void shouldEncode() {
    // given
    String expected =
        "u+WtKtUXd4CkUlfYJvL7Li4kr5LyNluP/m1Xqk4CMmTnsSymWTTpxpnwWD+RTseXJVsXUgrw6fZusGTjfS9knQ==";

    // when
    String encodedWithoutSlash = btcMarketsDigest.digest("path/to/method", "nonce", "test", null);
    String encodedWithSlash = btcMarketsDigest.digest("/path/to/method", "nonce", "test", null);

    // then
    assertThat(encodedWithoutSlash).isEqualTo(expected);
    assertThat(encodedWithSlash).isEqualTo(expected);
  }

  @Test
  public void shouldEncodeWithoutBody() {
    // given
    String expected =
        "/LEFVtbNw+pgTFK/thj4xWzKuNz16Tub2+Jm8Ooep4o3XH6tGalk6AQxFiUvnDmN+w3NQpu+qCyoO5ap6OseYQ==";

    // when
    String encodedEmpty = btcMarketsDigest.digest("/path/to/method", "nonce", "", null);
    String encodedNull = btcMarketsDigest.digest("/path/to/method", "nonce", null, null);

    // then
    assertThat(encodedEmpty).isEqualTo(expected);
    assertThat(encodedNull).isEqualTo(expected);
  }

  @Test
  public void shouldEncodeQueryString() {
    // given
    String expected =
        "bhjZf81rqrcSTrwVBZ1o34a2gALrO4yXT6u/zuN0PcfEJOehIDk1qnEID7UcRVttT3ePJkDMphixhBeusSqW8Q==";

    // when
    String encodedEmpty =
        btcMarketsDigest.digest(
            "/v2/order/trade/history/ETH/AUD",
            "nonce",
            "",
            "indexForward=true&limit=10&since=698825");
    String encodedNull =
        btcMarketsDigest.digest(
            "/v2/order/trade/history/ETH/AUD",
            "nonce",
            null,
            "indexForward=true&limit=10&since=698825");

    // then
    assertThat(encodedEmpty).isEqualTo(expected);
    assertThat(encodedNull).isEqualTo(expected);
  }

  @Test
  public void shouldEncodeRestInvocation() throws Exception {
    //    // given
    //    String expected =
    //
    // "wPYiZy9kIfRsexepi81dvv/eHv8fiyWdAoRSlaZrE3D63GbK3VOPRExKe5alTcNoldn2xd+7RS2avbCInTltlA==";
    //
    //    RestInvocation invocation = mock(RestInvocation.class);
    //    PowerMockito.when(
    //            invocation, "getParamValue", Mockito.eq(HeaderParam.class),
    // Mockito.eq("timestamp"))
    //        .thenReturn("nonce");
    //    PowerMockito.when(invocation, "getMethodPath").thenReturn("/path/to/method");
    //    PowerMockito.when(invocation, "getRequestBody").thenReturn("request body");
    //
    //    // when
    //    String encoded = btcMarketsDigest.digestParams(invocation);
    //
    //    // then
    //    assertThat(encoded).isEqualTo(expected);
  }
}
