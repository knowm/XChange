package org.knowm.xchange.gateio.dto.trade;

import org.knowm.xchange.gateio.dto.GateioBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GateioPlaceOrderReturn extends GateioBaseResponse {

  private final String orderId;

  /**
   * Constructor
   */
  private GateioPlaceOrderReturn(@JsonProperty("result") boolean result, @JsonProperty("order_id") String anOrderId,
      @JsonProperty("msg") String message) {

    super(result, message);
    orderId = anOrderId;
  }

  public String getOrderId() {

    return orderId;
  }

  @Override
  public String toString() {

    return "GateioPlaceOrderReturn [orderId=" + orderId + "]";
  }

}
