package org.knowm.xchange.dto.marketdata;

import org.junit.Assert;
import org.junit.Test;

public class TradesTest {

  @Test
  public void TradeIDComparator() {
    Trade t1 = new Trade(null, null, null, null, null, "99");
    Trade t2 = new Trade(null, null, null, null, null, "100");
    Trade t3 = new Trade(null, null, null, null, null, "abc");
    Trade t4 = new Trade(null, null, null, null, null, "zzz");
    Assert.assertTrue(new Trades.TradeIDComparator().compare(t1, t2) < 0);
    Assert.assertTrue(new Trades.TradeIDComparator().compare(t1, t3) < 0);
    Assert.assertTrue(new Trades.TradeIDComparator().compare(t2, t3) < 0);
    try {
      new Trades.TradeIDComparator().compare(t1, t3);
      new Trades.TradeIDComparator().compare(t3, t1);
      new Trades.TradeIDComparator().compare(t3, t4);
    } catch (Exception e) {
      Assert.fail("Could not compare trades");
    }
  }

}