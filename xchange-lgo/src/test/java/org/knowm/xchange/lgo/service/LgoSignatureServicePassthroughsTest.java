package org.knowm.xchange.lgo.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.lgo.dto.order.LgoOrderSignature;

public class LgoSignatureServicePassthroughsTest {

  private LgoSignatureServicePassthroughs signatureService;

  @Before
  public void setUp() {
    signatureService = new LgoSignatureServicePassthroughs("user", "key", "secret");
  }

  @Test
  public void itEncodesUserInformations() {
    String result = signatureService.digestSignedUrlHeader("url", "1234");

    assertThat(result).isEqualTo("dXNlcjprZXk6c2VjcmV0");
  }

  @Test
  public void putsIdentifierInOrderSignature() {
    LgoOrderSignature encryptedOrder = signatureService.signOrder("encryptedOrder");

    assertThat(encryptedOrder).isNotNull();
    assertThat(encryptedOrder.getSource()).isEqualTo("RSA");
    assertThat(encryptedOrder.getValue()).isEqualTo("dXNlcjprZXk6c2VjcmV0");
  }
}
