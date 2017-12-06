package org.knowm.xchange.gateio.dto.trade;

import org.knowm.xchange.gateio.dto.GateioBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GateioPlaceOrderReturn extends GateioBaseResponse {

  private final String orderNumber;

  /**
   * Constructor
   */
  private GateioPlaceOrderReturn(@JsonProperty("result") boolean result, @JsonProperty("orderNumber") String orderNumber,
      @JsonProperty("msg") String message) {

    super(result, message);
    this.orderNumber = orderNumber;
  }

  public String getOrderId() {

    return orderNumber;
  }

  @Override
  public String toString() {

    return "GateioPlaceOrderReturn [orderNumber=" + orderNumber + "]";
  }

}
