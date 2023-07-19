package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class VertexCancelProductOrdersMessage implements VertexRequest {
  private final CancelProductOrders cancelProductOrders;

  public VertexCancelProductOrdersMessage(CancelProductOrders cancelProductOrders) {
    this.cancelProductOrders = cancelProductOrders;
  }

  @JsonProperty("cancel_product_orders")
  public CancelProductOrders getCancelProductOrders() {
    return cancelProductOrders;
  }

  @Override
  public String getRequestType() {
    return "execute_cancel_product_orders";
  }

  @Override
  public String getSignature() {
    return cancelProductOrders.getSignature();
  }
}
