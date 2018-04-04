package org.knowm.xchange.liqui.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LiquiDepth {

  private final LiquiPublicAsks asks;
  private final LiquiPublicBids bids;

  public LiquiDepth(
      @JsonProperty("asks") final LiquiPublicAsks asks,
      @JsonProperty("bids") final LiquiPublicBids bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public List<LiquiPublicAsk> getAsks() {
    return asks.getAsks();
  }

  public List<LiquiPublicBid> getBids() {
    return bids.getBids();
  }

  @Override
  public String toString() {
    return "LiquiDepth{" + "asks=" + asks + ", bids=" + bids + '}';
  }
}
