package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxFundingRatePayload {

  @JsonProperty("start_time")
  private final Long start_time;

  @JsonProperty("end_time")
  private final Long end_time;

  @JsonProperty("future")
  private final String future;

  public FtxFundingRatePayload(
      @JsonProperty("start_time") Long start_time,
      @JsonProperty("end_time") Long end_time,
      @JsonProperty("future") String future) {
    this.start_time = start_time;
    this.end_time = end_time;
    this.future = future;
  }

  public Long getStart_time() {
    return start_time;
  }

  public Long getEnd_time() {
    return end_time;
  }

  public String getFuture() {
    return future;
  }

  @Override
  public String toString() {
    return "FtxFundingRatePayload{"
        + "start_time="
        + start_time
        + ", end_time="
        + end_time
        + ", future='"
        + future
        + '\''
        + '}';
  }
}
