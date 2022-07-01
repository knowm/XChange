package org.knowm.xchange.lgo.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LgoPlaceOrderResponse {

  public final String orderId;

  public LgoPlaceOrderResponse(@JsonProperty("order_id") String orderId) {
    this.orderId = orderId;
  }
}
