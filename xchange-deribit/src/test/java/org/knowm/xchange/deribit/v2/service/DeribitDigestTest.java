package org.knowm.xchange.deribit.v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.SynchronizedValueFactory;

@ExtendWith(MockitoExtension.class)
class DeribitDigestTest {

  @Mock
  RestInvocation restInvocation;

  @Test
  void signature_path() {
    DeribitDigest bitgetDigest = DeribitDigest.createDeribitAuth("id", "secret", new LongValueIncFactory());

    when(restInvocation.getHttpMethod()).thenReturn("GET");
    when(restInvocation.getPath()).thenReturn("/api/v2/private/get_positions");
    when(restInvocation.getQueryString()).thenReturn("");
    when(restInvocation.getRequestBody()).thenReturn("");

    String actual = bitgetDigest.digestParams(restInvocation);
    String expected = "deri-hmac-sha256 id=id,ts=1763296256773,sig=dd3eb83ab5101101cb37551e473fb22dcffb5db4c9e831acd8a9773e8924df9e,nonce=1763296256774";

    assertThat(actual).isEqualTo(expected);
  }


  @Test
  void signature_path_query() {
    DeribitDigest bitgetDigest = DeribitDigest.createDeribitAuth("id", "secret", new LongValueIncFactory());

    when(restInvocation.getHttpMethod()).thenReturn("GET");
    when(restInvocation.getPath()).thenReturn("/api/v2/private/get_account_summary");
    when(restInvocation.getQueryString()).thenReturn("currency=ETH&extended=false");
    when(restInvocation.getRequestBody()).thenReturn("");

    String actual = bitgetDigest.digestParams(restInvocation);
    String expected = "deri-hmac-sha256 id=id,ts=1763296256773,sig=29e8d4f59a20193bce58cecbeb4497415fe987fc344733645e15c5195dbdd16e,nonce=1763296256774";

    assertThat(actual).isEqualTo(expected);
  }


  private static class LongValueIncFactory implements SynchronizedValueFactory<Long> {
    private long i = 1763296256773L;

    @Override
    public Long createValue() {
      return i++;
    }
  }


}
