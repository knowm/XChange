package org.knowm.xchange.lgo.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class LgoOrderbook {

  private final long lastBatchId;
  private final List<Object[]> bids;
  private final List<Object[]> asks;

  public LgoOrderbook(
      @JsonProperty("batch_id") long lastBatchId,
      @JsonProperty("bids") List<Object[]> bids,
      @JsonProperty("asks") List<Object[]> asks) {
    this.lastBatchId = lastBatchId;
    this.bids = bids;
    this.asks = asks;
  }

  public long getLastBatchId() {
    return lastBatchId;
  }

  public List<Object[]> getBids() {
    return bids;
  }

  public List<Object[]> getAsks() {
    return asks;
  }
}
