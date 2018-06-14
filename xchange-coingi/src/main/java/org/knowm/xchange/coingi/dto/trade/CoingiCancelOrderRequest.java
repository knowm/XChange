package org.knowm.xchange.coingi.dto.trade;

import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;

public class CoingiCancelOrderRequest extends CoingiAuthenticatedRequest {
  private String orderId;

  public String getOrderId() {
    return orderId;
  }

  public CoingiCancelOrderRequest setOrderId(String orderId) {
    this.orderId = orderId;
    return this;
  }
}
