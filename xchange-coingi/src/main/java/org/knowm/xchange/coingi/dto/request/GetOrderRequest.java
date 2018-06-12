package org.knowm.xchange.coingi.dto.request;

public class GetOrderRequest extends AuthenticatedRequest {
  private String orderId;

  public String getOrderId() {
    return orderId;
  }

  public GetOrderRequest setOrderId(String orderId) {
    this.orderId = orderId;
    return this;
  }
}
