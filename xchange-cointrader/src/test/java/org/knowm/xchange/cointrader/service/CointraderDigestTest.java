package org.knowm.xchange.cointrader.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CointraderDigestTest {

  private final CointraderDigest hmac = new CointraderDigest("ks73GPO1qVPhIlFYeaCauiKwRMROpoL0DhwI7d3NcpNM");

  @Test
  public void testDigest1() throws Exception {
    test("{\"t\":\"Mon Apr 27 17:43:32 CEST 2015\"}", "945de89251b0781ada32d2a389f41684436482b529d45b3ee270355f978020f1");
  }

  @Test
  public void testDigest2() throws Exception {
    test("{\"t\":\"Mon Apr 27 17:51:14 CEST 2015\"}", "801f3410bed1a7823cd135e74318be946138bb920889fb9c234d9ea3e38903ac");
  }

  /*
   * @Test public void testDigest3a() throws Exception { test("{\"t\":\"Tue Apr 28 06:35:14 CEST 2015\",\"total_quantity\":0.03,\"price\":2000}",
   * "19a3ba7a48a604d56007a1fb3975a4010e2b238ce0285e96adeb60d10dd79dc2"); }
   */

  @Test
  public void testDigest3b() throws Exception {
    test("{\"t\":\"Tue Apr 28 06:35:14 CEST 2015\",\"total_quantity\":\"0.03\",\"price\":\"2000\"}",
        "19a3ba7a48a604d56007a1fb3975a4010e2b238ce0285e96adeb60d10dd79dc2");
  }

  private void test(String data, String expected) {
    assertEquals(expected, hmac.digest(data));
  }
}