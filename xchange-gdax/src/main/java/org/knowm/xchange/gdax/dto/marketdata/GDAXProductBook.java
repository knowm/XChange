package org.knowm.xchange.gdax.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;

/** Created by Yingzhe on 4/6/2015. */
public class GDAXProductBook {

  private final Long sequence;
  private final GDAXProductBookEntry[] bids;
  private final GDAXProductBookEntry[] asks;

  public GDAXProductBook(
      @JsonProperty("sequence") Long sequence,
      @JsonProperty("bids") Object[][] bids,
      @JsonProperty("asks") Object[][] asks) {

    this.sequence = sequence;

    if (bids != null && bids.length > 0) {
      this.bids = new GDAXProductBookEntry[bids.length];
      for (int i = 0; i < bids.length; i++) {
        this.bids[i] = convertToBookEntry(bids[i]);
      }
    } else {
      this.bids = null;
    }

    if (asks != null && asks.length > 0) {
      this.asks = new GDAXProductBookEntry[asks.length];
      for (int i = 0; i < asks.length; i++) {
        this.asks[i] = convertToBookEntry(asks[i]);
      }
    } else {
      this.asks = null;
    }
  }

  private static GDAXProductBookEntry convertToBookEntry(Object[] dataObject) {

    if (dataObject != null && dataObject.length == 3) {
      BigDecimal price = new BigDecimal((String) dataObject[0]);
      BigDecimal volume = new BigDecimal((String) dataObject[1]);

      // level 3 order book?
      if (dataObject[2] instanceof String) {
        return new GDAXProductBookEntryLevel3(price, volume, (String) dataObject[2]);
      } else { // level 1 or 2
        int numberOfOrders = (Integer) dataObject[2];
        return new GDAXProductBookEntryLevel1or2(price, volume, numberOfOrders);
      }
    }
    return null;
  }

  public Long getSequence() {

    return sequence;
  }

  public GDAXProductBookEntry[] getBids() {

    return bids;
  }

  public GDAXProductBookEntry[] getAsks() {

    return asks;
  }

  public GDAXProductBookEntry getBestBid() {

    if (this.getBids() != null && this.getBids().length > 0) {
      return this.getBids()[0];
    }
    return null;
  }

  public GDAXProductBookEntry getBestAsk() {

    if (this.getAsks() != null && this.getAsks().length > 0) {
      return this.getAsks()[0];
    }
    return null;
  }

  @Override
  public String toString() {
    return "GDAXProductBook [sequence="
        + sequence
        + ", bids="
        + Arrays.toString(bids)
        + ", asks="
        + Arrays.toString(asks)
        + "]";
  }
}
