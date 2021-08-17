package com.knowm.xchange.serum.structures;

import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

public class AccountFlagsLayout extends Struct {

  @Bin(order = 1, name = "bytes", type = BinType.BYTE_ARRAY)
  byte[] bytes;

  /** There is probably a cleaner way to do this but making it work for now */
  public AccountFlags decode() {
    boolean[] results = booleanFlagsDecoder(bytes, 7);
    return new AccountFlags(
        results[0], results[1], results[2], results[3], results[4], results[5], results[6]);
  }

  public static class AccountFlags {

    public boolean initialized;
    public boolean market;
    public boolean openOrders;
    public boolean requestQueue;
    public boolean eventQueue;
    public boolean bids;
    public boolean asks;

    AccountFlags(
        boolean initialized,
        boolean market,
        boolean openOrders,
        boolean requestQueue,
        boolean eventQueue,
        boolean bids,
        boolean asks) {
      this.initialized = initialized;
      this.market = market;
      this.openOrders = openOrders;
      this.requestQueue = requestQueue;
      this.eventQueue = eventQueue;
      this.bids = bids;
      this.asks = asks;
    }
  }
}
