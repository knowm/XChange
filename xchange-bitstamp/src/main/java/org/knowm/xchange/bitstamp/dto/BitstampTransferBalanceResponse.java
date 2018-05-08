package org.knowm.xchange.bitstamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitstampTransferBalanceResponse {

  private final String status;
  private String reason;

  protected BitstampTransferBalanceResponse(
      @JsonProperty("status") String status, @JsonProperty("reason") String reason) {
    this.status = status;
    this.reason = reason;
  }

  public String getstatus() {
    return status;
  }

  public String getReason() {
    return reason;
  }
}
