package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GateioFundingRateHistory {

  private final long timestamp;
  private final String rate;

  public GateioFundingRateHistory(
      @JsonProperty("t") long timestamp,
      @JsonProperty("r") String rate) {
    this.timestamp = timestamp;
    this.rate = rate;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getRate() {
    return rate;
  }

  @Override
  public String toString() {
    return "GateioFundingRateHistory{" +
        "timestamp=" + timestamp +
        ", rate='" + rate + '\'' +
        '}';
  }
}
