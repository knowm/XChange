package org.knowm.xchange.coingi.dto.trade;

import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;

public class CoingiGetOrderRequest extends CoingiAuthenticatedRequest {
  private String orderId;

  public String getOrderId() {
    return orderId;
  }

  public CoingiGetOrderRequest setOrderId(String orderId) {
    this.orderId = orderId;
    return this;
  }
}
