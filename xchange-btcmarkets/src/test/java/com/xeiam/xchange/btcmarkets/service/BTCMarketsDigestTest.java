package com.xeiam.xchange.btcmarkets.service;

import net.iharder.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.HeaderParam;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestInvocation.class)
@PowerMockIgnore("javax.crypto.*")
public class BTCMarketsDigestTest {

  private BTCMarketsDigest btcMarketsDigest;
  private Mac mac;


  @Before
  public void setUp() throws Exception {
    SecretKey secretKey = new SecretKeySpec("secretKey".getBytes(), "HmacSHA512");
    mac = Mac.getInstance("HmacSHA512");
    mac.init(secretKey);

    btcMarketsDigest = new BTCMarketsDigest("c2VjcmV0S2V5");  // encoded 'secretKey'
  }

  @Test
  public void shouldEncode() throws Exception {
    // given
    String expected = "u+WtKtUXd4CkUlfYJvL7Li4kr5LyNluP/m1Xqk4CMmTnsSymWTTpxpnwWD+RTseXJVsXUgrw6fZusGTjfS9knQ==";

    // when
    String encodedWithoutSlash = btcMarketsDigest.digest("path/to/method", "nonce", "test");
    String encodedWithSlash = btcMarketsDigest.digest("/path/to/method", "nonce", "test");

    // then
    assertThat(encodedWithoutSlash).isEqualTo(expected);
    assertThat(encodedWithSlash).isEqualTo(expected);
  }

  @Test
  public void shouldEncodeWithoutBody() throws Exception {
    // given
    String expected = "/LEFVtbNw+pgTFK/thj4xWzKuNz16Tub2+Jm8Ooep4o3XH6tGalk6AQxFiUvnDmN+w3NQpu+qCyoO5ap6OseYQ==";

    // when
    String encodedEmpty = btcMarketsDigest.digest("/path/to/method", "nonce", "");
    String encodedNull = btcMarketsDigest.digest("/path/to/method", "nonce", null);

    // then
    assertThat(encodedEmpty).isEqualTo(expected);
    assertThat(encodedNull).isEqualTo(expected);
  }

  @Test
  public void shouldEncodeRestInvocation() throws Exception {
    // given
    String expected = "wPYiZy9kIfRsexepi81dvv/eHv8fiyWdAoRSlaZrE3D63GbK3VOPRExKe5alTcNoldn2xd+7RS2avbCInTltlA==";

    RestInvocation invocation = mock(RestInvocation.class);
    PowerMockito.when(invocation, "getParamValue", Mockito.eq(HeaderParam.class), Mockito.eq("timestamp")).thenReturn("nonce");
    PowerMockito.when(invocation, "getMethodPath").thenReturn("/path/to/method");
    PowerMockito.when(invocation, "getRequestBody").thenReturn("request body");

    // when
    String encoded = btcMarketsDigest.digestParams(invocation);

    // then
    assertThat(encoded).isEqualTo(expected);
  }
}
