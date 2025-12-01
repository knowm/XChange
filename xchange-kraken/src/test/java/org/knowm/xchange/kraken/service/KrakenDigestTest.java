package org.knowm.xchange.kraken.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import si.mazi.rescu.RestInvocation;

@ExtendWith(MockitoExtension.class)
class KrakenDigestTest {

  @Mock RestInvocation restInvocation;

  @Test
  void signature() {
    var krakenDigest = KrakenDigest.createInstance("abc");

    when(restInvocation.getPath()).thenReturn("/a");
    when(restInvocation.getRequestBody()).thenReturn("{\"nonce\":\"1756856074101\"}");
    when(restInvocation.getParamValue(any(), eq("nonce"))).thenReturn("1756856074101");

    String actual = krakenDigest.digestParams(restInvocation);
    String expected =
        "M5ex0kvOBkp+CCYtWJ1k0ivHSfdXphqHCxc98jzwLW/BOwlH4dowsIDzAya2y/PgRQJuxTKA49IYjgnlBPO3Vw==";

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void signatureWithJsonBody() {
    var krakenDigest = KrakenDigest.createInstance("abc");

    when(restInvocation.getPath()).thenReturn("/0/private/Earn/Allocations");
    when(restInvocation.getRequestBody())
        .thenReturn("{\"nonce\":\"1756856074101\",\"ascending\":true}");
    when(restInvocation.getParamValue(any(), eq("nonce"))).thenReturn(null);

    String actual = krakenDigest.digestParams(restInvocation);

    // Verify signature is generated and is a valid base64 string
    assertThat(actual).isNotNull().isNotEmpty();
    // The signature should be different from the FormParam version due to different path
    // but should still be a valid base64 encoded string
    assertThat(actual).matches("^[A-Za-z0-9+/]+=*$");
  }
}
