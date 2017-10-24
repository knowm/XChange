package org.knowm.xchange.service.trade.params;

public class CancelOrderByIdParams implements CancelOrderParams {
  public final String orderId;

  public CancelOrderByIdParams(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
