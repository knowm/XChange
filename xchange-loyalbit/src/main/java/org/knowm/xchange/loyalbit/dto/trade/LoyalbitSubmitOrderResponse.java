package org.knowm.xchange.loyalbit.dto.trade;

import org.knowm.xchange.loyalbit.dto.LoyalbitBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoyalbitSubmitOrderResponse extends LoyalbitBaseResponse {

  @JsonProperty("order_id")
  private Long orderId;

  public LoyalbitSubmitOrderResponse(@JsonProperty("status") Integer status, @JsonProperty("message") String message,
      @JsonProperty("order_id") Long orderId) {
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
