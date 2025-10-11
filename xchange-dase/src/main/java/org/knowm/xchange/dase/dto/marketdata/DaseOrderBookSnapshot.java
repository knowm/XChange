package org.knowm.xchange.dase.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class DaseOrderBookSnapshot {

  private final long timestamp;
  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;
  private final Long eventId;

  @JsonCreator
  public DaseOrderBookSnapshot(
      @JsonProperty("timestamp") Number timestamp,
      @JsonProperty("bids") List<List<BigDecimal>> bids,
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("event_id") Long eventId) {
    this.timestamp = timestamp == null ? 0L : timestamp.longValue();
    this.bids = bids;
    this.asks = asks;
    this.eventId = eventId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  public Long getEventId() {
    return eventId;
  }
}
