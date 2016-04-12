package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExProductBook {

  private final Long sequence;
  private final CoinbaseExProductBookEntry[] bids;
  private final CoinbaseExProductBookEntry[] asks;

  public CoinbaseExProductBook(@JsonProperty("sequence") Long sequence, @JsonProperty("bids") Object[][] bids,
      @JsonProperty("asks") Object[][] asks) {

    this.sequence = sequence;

    if (bids != null && bids.length > 0) {
      this.bids = new CoinbaseExProductBookEntry[bids.length];
      for (int i = 0; i < bids.length; i++) {
        this.bids[i] = convertToBookEntry(bids[i]);
      }
    } else {
      this.bids = null;
    }

    if (asks != null && asks.length > 0) {
      this.asks = new CoinbaseExProductBookEntry[asks.length];
      for (int i = 0; i < asks.length; i++) {
        this.asks[i] = convertToBookEntry(asks[i]);
      }
    } else {
      this.asks = null;
    }
  }

  public Long getSequence() {

    return sequence;
  }

  public CoinbaseExProductBookEntry[] getBids() {

    return bids;
  }

  public CoinbaseExProductBookEntry[] getAsks() {

    return asks;
  }

  public CoinbaseExProductBookEntry getBestBid() {

    if (this.getBids() != null && this.getBids().length > 0) {
      return this.getBids()[0];
    }
    return null;

  }

  public CoinbaseExProductBookEntry getBestAsk() {

    if (this.getAsks() != null && this.getAsks().length > 0) {
      return this.getAsks()[0];
    }
    return null;
  }

  private static CoinbaseExProductBookEntry convertToBookEntry(Object[] dataObject) {

    if (dataObject != null && dataObject.length == 3) {
      BigDecimal price = new BigDecimal((String) dataObject[0]);
      BigDecimal volume = new BigDecimal((String) dataObject[1]);
      int numberOfOrders = (Integer) dataObject[2];
      return new CoinbaseExProductBookEntry(price, volume, numberOfOrders);
    }
    return null;
  }
}
