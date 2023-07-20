package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VertexCancelOrdersMessage implements VertexRequest {

  private final CancelOrders cancelOrders;

  public VertexCancelOrdersMessage(@JsonProperty("cancel_orders") CancelOrders cancelOrders) {
    this.cancelOrders = cancelOrders;
  }

  @JsonProperty("cancel_orders")
  public CancelOrders getCancelOrders() {
    return cancelOrders;
  }

  @Override
  public String getRequestType() {
    return "execute_cancel_orders";
  }

  @Override
  public String getSignature() {
    return cancelOrders.getSignature();
  }
}


