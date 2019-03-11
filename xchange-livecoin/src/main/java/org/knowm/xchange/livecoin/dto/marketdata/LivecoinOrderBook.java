package org.knowm.xchange.livecoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import org.knowm.xchange.livecoin.service.LivecoinAsksBidsData;

public class LivecoinOrderBook {

  private final Long timestamp;
  private final LivecoinAsksBidsData[] asks;
  private final LivecoinAsksBidsData[] bids;

  public LivecoinOrderBook(
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("asks") Object[][] asks,
      @JsonProperty("bids") Object[][] bids) {
    super();

    this.timestamp = timestamp;

    if (bids != null && bids.length > 0) {
      this.bids = new LivecoinAsksBidsData[bids.length];
      for (int i = 0; i < bids.length; i++) {
        this.bids[i] = convertToOrderBookEntry(bids[i]);
      }
    } else {
      this.bids = null;
    }

    if (asks != null && asks.length > 0) {
      this.asks = new LivecoinAsksBidsData[asks.length];
      for (int i = 0; i < asks.length; i++) {
        this.asks[i] = convertToOrderBookEntry(asks[i]);
      }
    } else {
      this.asks = null;
    }
  }

  private static LivecoinAsksBidsData convertToOrderBookEntry(Object[] dataObject) {
    if (dataObject != null && dataObject.length == 2) {
      BigDecimal volume = new BigDecimal((String) dataObject[0]);
      BigDecimal price = new BigDecimal((String) dataObject[1]);

      return new LivecoinAsksBidsData(price, volume);
    }
    return null;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public LivecoinAsksBidsData[] getAsks() {
    return asks;
  }

  public LivecoinAsksBidsData[] getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "LivecoinOrderBook [timestamp="
        + timestamp
        + ", asks="
        + Arrays.toString(asks)
        + ", bids="
        + Arrays.toString(bids)
        + "]";
  }
}
