package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Response for placing an order. */
public class DasePlaceOrderResponse {

  private final String orderId;

  @JsonCreator
  public DasePlaceOrderResponse(@JsonProperty("order_id") String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
