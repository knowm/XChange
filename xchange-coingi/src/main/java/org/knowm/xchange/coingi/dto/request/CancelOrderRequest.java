package org.knowm.xchange.coingi.dto.request;

public class CancelOrderRequest extends AuthenticatedRequest {
  private String orderId;

  public String getOrderId() {
    return orderId;
  }

  public CancelOrderRequest setOrderId(String orderId) {
    this.orderId = orderId;
    return this;
  }
}
