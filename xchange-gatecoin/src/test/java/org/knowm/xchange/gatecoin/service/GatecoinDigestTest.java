package org.knowm.xchange.gatecoin.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GatecoinDigestTest {

  @Test
  public void shouldComputeCorrectSignature() throws Exception {
    final GatecoinDigest gcd = GatecoinDigest.createInstance("N3UYyg2Ar7rDzctx1e3eeTNrFjc4wViP");

    final String signature =
        gcd.digest("GET", "https://www.gatecoin.com/api/balance/balances", "", "1440640469.1338");

    assertThat(signature).isEqualTo("lFE0lkJicPysGQopNrG3Ln2NQPkAaV/kZdpnsGWTzVM=");
  }
}
