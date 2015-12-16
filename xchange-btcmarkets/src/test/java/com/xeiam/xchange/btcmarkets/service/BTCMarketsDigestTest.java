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
    // when
    mac.update("/path/to/method\nnonce\ntest".getBytes());
    String expected = Base64.encodeBytes(mac.doFinal());

    // then
    assertThat(btcMarketsDigest.digest("path/to/method", "nonce", "test")).isEqualTo(expected);
    assertThat(btcMarketsDigest.digest("/path/to/method", "nonce", "test")).isEqualTo(expected);
  }

  @Test
  public void shouldEncodeWithoutBody() throws Exception {
    // when
    mac.update("/path/to/method\nnonce\n".getBytes());
    String expected = Base64.encodeBytes(mac.doFinal());

    // then
    assertThat(btcMarketsDigest.digest("/path/to/method", "nonce", "")).isEqualTo(expected);
    assertThat(btcMarketsDigest.digest("/path/to/method", "nonce", null)).isEqualTo(expected);
  }

  @Test
  public void shouldEncodeRestInvocation() throws Exception {
    // given
    mac.update("/path/to/method\nnonce\nrequest body".getBytes());
    String expected = Base64.encodeBytes(mac.doFinal());
    RestInvocation invocation = mock(RestInvocation.class);

    // when
    PowerMockito.when(invocation, "getParamValue", Mockito.eq(HeaderParam.class), Mockito.eq("timestamp")).thenReturn("nonce");
    PowerMockito.when(invocation, "getMethodPath").thenReturn("/path/to/method");
    PowerMockito.when(invocation, "getRequestBody").thenReturn("request body");

    // then
    assertThat(btcMarketsDigest.digestParams(invocation)).isEqualTo(expected);
  }


}

//
