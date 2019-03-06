package org.knowm.xchange.lykke.dto.trade;

import org.knowm.xchange.dto.Order;

public enum LykkeTradeState {
  InProgress(Order.OrderStatus.PENDING_NEW),
  Finished(Order.OrderStatus.FILLED),
  Canceled(Order.OrderStatus.CANCELED),
  Failed(Order.OrderStatus.REJECTED);

  private Order.OrderStatus orderStatus;

  private LykkeTradeState(Order.OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public static LykkeTradeState fromOrderStatus(Order.OrderStatus orderStatus) {
    if (orderStatus.equals(Order.OrderStatus.PENDING_NEW)) {
      return InProgress;
    } else if (orderStatus.equals(Order.OrderStatus.FILLED)) {
      return Finished;
    } else if (orderStatus.equals(Order.OrderStatus.CANCELED)) {
      return Canceled;
    } else if (orderStatus.equals(Order.OrderStatus.REJECTED)) {
      return Failed;
    } else {
      throw new UnsupportedOperationException("Lykke does not support OrderStatus " + orderStatus);
    }
  }

  public static Order.OrderStatus toOrderStatus(LykkeTradeState lykkeTradeState) {
    if (lykkeTradeState.equals(InProgress)) {
      return Order.OrderStatus.PENDING_NEW;
    } else if (lykkeTradeState.equals(Finished)) {
      return Order.OrderStatus.FILLED;
    } else if (lykkeTradeState.equals(Canceled)) {
      return Order.OrderStatus.CANCELED;
    } else if (lykkeTradeState.equals(Failed)) {
      return Order.OrderStatus.REJECTED;
    } else {
      throw new UnsupportedOperationException(
          "Lykke does not support LykkeTradeState "
              + lykkeTradeState
              + " you should implement it.");
    }
  }

  public Order.OrderStatus getOrderStatus() {
    return orderStatus;
  }
}
