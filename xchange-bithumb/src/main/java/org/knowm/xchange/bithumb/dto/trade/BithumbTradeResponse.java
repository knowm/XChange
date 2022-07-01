package org.knowm.xchange.bithumb.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BithumbTradeResponse {

  private final String status;
  private final String message;
  private final String orderId;

  public BithumbTradeResponse(
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("order_id") String orderId) {
    this.status = status;
    this.message = message;
    this.orderId = orderId;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getOrderId() {
    return orderId;
  }
}
