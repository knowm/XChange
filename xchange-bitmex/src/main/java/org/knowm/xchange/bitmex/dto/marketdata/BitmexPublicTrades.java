package org.knowm.xchange.bitmex.dto.marketdata;

import java.util.List;

//@JsonDeserialize(using = BitmexTradesDeserializer.class)
public class BitmexPublicTrades {

  private final List<BitmexPublicTrade> trades;
  // private final long last;

  public BitmexPublicTrades(List<BitmexPublicTrade> trades) {

    this.trades = trades;

  }

  public long getLast() {

    return 0;
  }

  public List<BitmexPublicTrade> getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    return "BitmexTrades [trades=" + trades + ", last=" + 0 + "]";
  }

}
