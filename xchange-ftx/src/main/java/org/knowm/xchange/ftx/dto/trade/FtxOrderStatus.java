package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FtxOrderStatus {
  NEW("new"),
  OPEN("open"),
  CLOSED("closed"),
  CANCELLED("cancelled"),
  TRIGGERED("triggered");

  private final String status;

  FtxOrderStatus(String status) {
    this.status = status;
  }

  @JsonValue
  public String getStatus() {
    return status;
  }
}
