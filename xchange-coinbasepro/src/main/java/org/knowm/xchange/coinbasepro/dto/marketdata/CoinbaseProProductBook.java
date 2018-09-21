package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;

public class CoinbaseProProductBook {

  private final Long sequence;
  private final CoinbaseProProductBookEntry[] bids;
  private final CoinbaseProProductBookEntry[] asks;

  public CoinbaseProProductBook(
      @JsonProperty("sequence") Long sequence,
      @JsonProperty("bids") Object[][] bids,
      @JsonProperty("asks") Object[][] asks) {

    this.sequence = sequence;

    if (bids != null && bids.length > 0) {
      this.bids = new CoinbaseProProductBookEntry[bids.length];
      for (int i = 0; i < bids.length; i++) {
        this.bids[i] = convertToBookEntry(bids[i]);
      }
    } else {
      this.bids = null;
    }

    if (asks != null && asks.length > 0) {
      this.asks = new CoinbaseProProductBookEntry[asks.length];
      for (int i = 0; i < asks.length; i++) {
        this.asks[i] = convertToBookEntry(asks[i]);
      }
    } else {
      this.asks = null;
    }
  }

  private static CoinbaseProProductBookEntry convertToBookEntry(Object[] dataObject) {

    if (dataObject != null && dataObject.length == 3) {
      BigDecimal price = new BigDecimal((String) dataObject[0]);
      BigDecimal volume = new BigDecimal((String) dataObject[1]);

      // level 3 order book?
      if (dataObject[2] instanceof String) {
        return new CoinbaseProProductBookEntryLevel3(price, volume, (String) dataObject[2]);
      } else { // level 1 or 2
        int numberOfOrders = (Integer) dataObject[2];
        return new CoinbaseProProductBookEntryLevel1or2(price, volume, numberOfOrders);
      }
    }
    return null;
  }

  public Long getSequence() {

    return sequence;
  }

  public CoinbaseProProductBookEntry[] getBids() {

    return bids;
  }

  public CoinbaseProProductBookEntry[] getAsks() {

    return asks;
  }

  public CoinbaseProProductBookEntry getBestBid() {

    if (getBids() != null && getBids().length > 0) {
      return getBids()[0];
    }
    return null;
  }

  public CoinbaseProProductBookEntry getBestAsk() {

    if (getAsks() != null && getAsks().length > 0) {
      return getAsks()[0];
    }
    return null;
  }

  @Override
  public String toString() {
    return "CoinbaseProProductBook [sequence="
        + sequence
        + ", bids="
        + Arrays.toString(bids)
        + ", asks="
        + Arrays.toString(asks)
        + "]";
  }
}
