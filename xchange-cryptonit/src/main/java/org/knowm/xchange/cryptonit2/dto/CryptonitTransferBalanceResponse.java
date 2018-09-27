package org.knowm.xchange.cryptonit2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptonitTransferBalanceResponse {

  private final String status;
  private String reason;

  protected CryptonitTransferBalanceResponse(
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
