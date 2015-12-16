package com.xeiam.xchange.bleutrade.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestInvocation.class)
@PowerMockIgnore("javax.crypto.*")
public class BleutradeDigestTest {

  private BleutradeDigest bleutradeDigest;
  private Mac mac;

  @Before
  public void setUp() throws Exception {
    SecretKey secretKey = new SecretKeySpec("secretKey".getBytes(), "HmacSHA512");
    mac = Mac.getInstance("HmacSHA512");
    mac.init(secretKey);

    bleutradeDigest = BleutradeDigest.createInstance("secretKey");
  }

  @Test
  public void shouldEncodeRestInvocation() throws Exception {
    // given
    mac.update("rest body".getBytes());
    String expected = String.format("%0128x", new BigInteger(1, mac.doFinal()));
    RestInvocation invocation = mock(RestInvocation.class);

    // when
    PowerMockito.when(invocation, "getInvocationUrl").thenReturn("rest body");

    // then
    assertThat(bleutradeDigest.digestParams(invocation)).isEqualTo(expected);
  }

}

