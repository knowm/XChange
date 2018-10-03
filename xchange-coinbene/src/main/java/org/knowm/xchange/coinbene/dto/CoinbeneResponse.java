package org.knowm.xchange.coinbene.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbeneResponse {
  @JsonProperty("status")
  private Status status;

  @JsonProperty("timestamp")
  private long timestamp;

  @JsonProperty("description")
  private String errorDescription;

  public boolean isOk() {
    return Status.ok == status;
  }

  public Status getStatus() {
    return status;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public enum Status {
    error,
    ok
  }
}
