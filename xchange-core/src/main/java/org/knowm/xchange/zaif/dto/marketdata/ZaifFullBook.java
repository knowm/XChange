package org.knowm.xchange.zaif.dto.marketdata;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZaifFullBook {

  private final ZaifFullBookTier[] bids;
  private final ZaifFullBookTier[] asks;

  public ZaifFullBook(@JsonProperty("bids") Object[][] bids, @JsonProperty("asks") Object[][] asks) {
    if (bids != null && bids.length > 0) {
      this.bids = new ZaifFullBookTier[bids.length];

      for (int i = 0; i < bids.length; i++) {
        this.bids[i] = new ZaifFullBookTier(bids[i]);
      }
    } else {
      this.bids = null;
    }

    if (asks != null && asks.length > 0) {
      this.asks = new ZaifFullBookTier[asks.length];
      for (int i = 0; i < asks.length; i++) {
        this.asks[i] = new ZaifFullBookTier(asks[i]);
      }
    } else {
      this.asks = null;
    }
  }

  public ZaifFullBookTier[] getBids() {
    return this.bids;
  }

  public ZaifFullBookTier[] getAsks() {
    return this.asks;
  }

  @Override
  public String toString() {
    return "ZaifFullBook [bids: " + Arrays.toString(this.getBids()) + ", asks: " + Arrays.toString(this.getAsks()) + "]";
  }
}
