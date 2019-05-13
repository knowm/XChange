package org.knowm.xchange.livecoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

public class LivecoinOrderBook {

  private final Long timestamp;
  private final List<LivecoinOrder> asks;
  private final List<LivecoinOrder> bids;

  public LivecoinOrderBook(
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("asks") @JsonDeserialize(using = LivecoinOrdersDeserializer.class)
          List<LivecoinOrder> asks,
      @JsonProperty("bids") @JsonDeserialize(using = LivecoinOrdersDeserializer.class)
          List<LivecoinOrder> bids) {
    super();

    this.timestamp = timestamp;
    this.asks = asks;
    this.bids = bids;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public List<LivecoinOrder> getAsks() {
    return asks;
  }

  public List<LivecoinOrder> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "LivecoinOrderBook [timestamp=" + timestamp + ", asks=" + asks + ", bids=" + bids + "]";
  }
}
