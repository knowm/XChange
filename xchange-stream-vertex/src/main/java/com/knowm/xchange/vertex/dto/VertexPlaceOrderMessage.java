package com.knowm.xchange.vertex.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VertexPlaceOrderMessage implements VertexRequest {
  private final VertexPlaceOrder place_order;

  public VertexPlaceOrderMessage(VertexPlaceOrder placeOrder) {
    place_order = placeOrder;
  }

  @Override
  public String getRequestType() {
    return "execute_place_order";
  }

  @Override
  public String getSignature() {
    return place_order.getSignature();
  }
}
