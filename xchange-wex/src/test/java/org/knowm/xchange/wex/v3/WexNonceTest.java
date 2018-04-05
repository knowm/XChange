package org.knowm.xchange.wex.v3;

import org.junit.Assert;
import org.junit.Test;
import si.mazi.rescu.SynchronizedValueFactory;

public class WexNonceTest {

  private int prevNonce;

  @Test
  public void testNonceSuccession() throws Exception {

    SynchronizedValueFactory<Long> nf1 = new WexExchange().getNonceFactory();

    // Get a few nonces from the same factory in quick succession.
    assertNonceLarger(nf1);
    assertNonceLarger(nf1);
    assertNonceLarger(nf1);
    assertNonceLarger(nf1);

    // A nonce factory created a bit later should return compatible nonces.
    Thread.sleep(1500);

    SynchronizedValueFactory<Long> nf2 = new WexExchange().getNonceFactory();

    assertNonceLarger(nf2);
    assertNonceLarger(nf2);
    assertNonceLarger(nf2);
    assertNonceLarger(nf2);

    // After a short pause, the first nonce factory should again return valid nonces.
    Thread.sleep(1500);
    assertNonceLarger(nf1);
    assertNonceLarger(nf1);
    assertNonceLarger(nf1);
  }

  private void assertNonceLarger(SynchronizedValueFactory<Long> nonceFactory) {
    final int nonce = nonceFactory.createValue().intValue();
    Assert.assertTrue(nonce > prevNonce);
    prevNonce = nonce;
  }
}
