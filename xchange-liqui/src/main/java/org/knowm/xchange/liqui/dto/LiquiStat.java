package org.knowm.xchange.liqui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquiStat {

  private final boolean success;
  private final String serverTime;
  private final String time;
  private final String errors;

  public LiquiStat(
      @JsonProperty("isSuccess") final boolean success,
      @JsonProperty("serverTime") final String serverTime,
      @JsonProperty("time") final String time,
      @JsonProperty("errors") final String errors) {
    this.success = success;
    this.serverTime = serverTime;
    this.time = time;
    this.errors = errors;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getServerTime() {
    return serverTime;
  }

  public String getTime() {
    return time;
  }

  public String getErrors() {
    return errors;
  }

  @Override
  public String toString() {
    return "LiquiStat{"
        + "success="
        + success
        + ", serverTime='"
        + serverTime
        + '\''
        + ", time='"
        + time
        + '\''
        + ", errors='"
        + errors
        + '\''
        + '}';
  }
}
