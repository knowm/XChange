package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;

public class OkexOrderBook {

  private final String timestamp;
  private final OkexOrderBookEntry[] bids;
  private final OkexOrderBookEntry[] asks;

  public OkexOrderBook(
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("bids") Object[][] bids,
      @JsonProperty("asks") Object[][] asks) {

    this.timestamp = timestamp;

    if (bids != null && bids.length > 0) {
      this.bids = new OkexOrderBookEntry[bids.length];
      for (int i = 0; i < bids.length; i++) {
        this.bids[i] = convertToBookEntry(bids[i]);
      }
    } else {
      this.bids = null;
    }

    if (asks != null && asks.length > 0) {
      this.asks = new OkexOrderBookEntry[asks.length];
      for (int i = 0; i < asks.length; i++) {
        this.asks[i] = convertToBookEntry(asks[i]);
      }
    } else {
      this.asks = null;
    }
  }

  private static OkexOrderBookEntry convertToBookEntry(Object[] dataObject) {

    if (dataObject != null && dataObject.length == 3) {
      BigDecimal price = new BigDecimal((String) dataObject[0]);
      BigDecimal volume = new BigDecimal((String) dataObject[1]);
      String numberOfOrders = (String) dataObject[2];
      return new OkexOrderBookEntry(price, volume, numberOfOrders);
    }
    return null;
  }

  public String getTimestamp() {

    return timestamp;
  }

  public OkexOrderBookEntry[] getBids() {

    return bids;
  }

  public OkexOrderBookEntry[] getAsks() {

    return asks;
  }

  @Override
  public String toString() {
    return "OkexOrderBook [timestamp="
        + timestamp
        + ", bids="
        + Arrays.toString(bids)
        + ", asks="
        + Arrays.toString(asks)
        + "]";
  }
}
