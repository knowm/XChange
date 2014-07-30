package com.xeiam.xchange.bter.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bter.dto.BTERBaseResponse;

public class BTERPlaceOrderReturn extends BTERBaseResponse {

  private final String orderId;

  /**
   * Constructor
   * 
   * @param success
   * @param value
   * @param error
   */
  private BTERPlaceOrderReturn(@JsonProperty("result") boolean result, @JsonProperty("order_id") String anOrderId, @JsonProperty("msg") String message) {

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
