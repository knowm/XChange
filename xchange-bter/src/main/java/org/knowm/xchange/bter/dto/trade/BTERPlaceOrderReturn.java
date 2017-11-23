package org.knowm.xchange.bter.dto.trade;

import org.knowm.xchange.bter.dto.BTERBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTERPlaceOrderReturn extends BTERBaseResponse {

  private final String orderId;

  /**
   * Constructor
   */
  private BTERPlaceOrderReturn(@JsonProperty("result") boolean result, @JsonProperty("order_id") String anOrderId,
      @JsonProperty("msg") String message) {

    super(result, message);
    orderId = anOrderId;
  }

  public String getOrderId() {

    return orderId;
  }

  @Override
  public String toString() {

    return "BTERPlaceOrderReturn [orderId=" + orderId + "]";
  }

}
