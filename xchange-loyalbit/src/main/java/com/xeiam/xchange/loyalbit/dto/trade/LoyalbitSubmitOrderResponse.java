package com.xeiam.xchange.loyalbit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.loyalbit.dto.LoyalbitBaseResponse;

public class LoyalbitSubmitOrderResponse extends LoyalbitBaseResponse {

  @JsonProperty("order_id")
  private Long orderId;

  public LoyalbitSubmitOrderResponse(
      @JsonProperty("status") Integer status,
      @JsonProperty("message") String message,
      @JsonProperty("order_id") Long orderId
  ) {
    super(status, message);
    this.orderId = orderId;
  }

  public Long getOrderId() {
    return orderId;
  }

  @Override
  public String toString() {
    return String.format("LoyalbitSubmitOrderResponse{orderId=%d}", orderId);
  }
}
