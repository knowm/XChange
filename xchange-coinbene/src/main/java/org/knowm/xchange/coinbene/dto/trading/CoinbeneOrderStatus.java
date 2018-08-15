package org.knowm.xchange.coinbene.dto.trading;

import org.knowm.xchange.dto.Order;

public enum CoinbeneOrderStatus {
  filled(Order.OrderStatus.FILLED),
  unfilled(Order.OrderStatus.NEW),
  partialFilled(Order.OrderStatus.PARTIALLY_FILLED),
  canceled(Order.OrderStatus.CANCELED),
  partialCanceled(Order.OrderStatus.PARTIALLY_CANCELED);

  private final Order.OrderStatus status;

  CoinbeneOrderStatus(Order.OrderStatus status) {
    this.status = status;
  }

  public Order.OrderStatus getStatus() {
    return status;
  }
}
