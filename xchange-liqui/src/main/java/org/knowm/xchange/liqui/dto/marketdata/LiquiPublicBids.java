package org.knowm.xchange.liqui.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LiquiPublicBids {

  private final List<LiquiPublicBid> bids;

  @JsonCreator
  public LiquiPublicBids(final List<LiquiPublicBid> bids) {
    this.bids = bids;
  }

  public List<LiquiPublicBid> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "LiquiPublicBids{" +
        "bids=" + bids +
        '}';
  }
}
