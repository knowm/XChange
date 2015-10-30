package com.xeiam.xchange.gatecoin.service;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class GatecoinDigestTest {

  @Test
  public void shouldComputeCorrectSignature() throws Exception {
    final GatecoinDigest gcd = GatecoinDigest.createInstance("N3UYyg2Ar7rDzctx1e3eeTNrFjc4wViP");

    final String signature = gcd.digest("GET", "https://www.gatecoin.com/api/balance/balances", "", "1440640469.1338");

    assertThat(signature).isEqualTo("lFE0lkJicPysGQopNrG3Ln2NQPkAaV/kZdpnsGWTzVM=");
  }
}