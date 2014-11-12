package com.xeiam.xchange.btce.v2.service.polling;


import org.junit.Assert;

import org.junit.Test;

import com.xeiam.xchange.ExchangeSpecification;

public class BTCEBasePollingServiceTest {

  private int lastNonce;

  @SuppressWarnings("unchecked")
  private BTCEBasePollingService ps = new BTCEBasePollingService(new ExchangeSpecification(""));

  @Test
  public void testNextNonce() throws Exception {

    getNextNonce();
    // Get a few nonces in quick succession. Expect each to be 1 larger than the previous.
    Assert.assertEquals(1, getNextNonce());
    Assert.assertEquals(1, getNextNonce());
    Assert.assertEquals(1, getNextNonce());
    Assert.assertEquals(1, getNextNonce());

    // A bit later, the nonce should increase more with time.
    Thread.sleep(1500);
    Assert.assertTrue(getNextNonce() > 1);
  }

  private int getNextNonce() {
    final int nextNonce = ps.nextNonce();
    Assert.assertTrue(nextNonce > lastNonce);
    final int diff = nextNonce - lastNonce;
    lastNonce = nextNonce;
    return diff;
  }
}