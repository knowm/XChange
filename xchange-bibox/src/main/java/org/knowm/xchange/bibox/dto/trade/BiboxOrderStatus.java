package org.knowm.xchange.bibox.dto.trade;

import org.knowm.xchange.dto.Order.OrderStatus;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author odrotleff
 */
public enum BiboxOrderStatus {
  PENDING(1, OrderStatus.NEW),
  PART_COMPLETED(2, OrderStatus.PARTIALLY_FILLED),
  COMPLETED(3, OrderStatus.FILLED),
  PART_CANCELED(4, OrderStatus.PARTIALLY_CANCELED),
  CANCELLED(5, OrderStatus.CANCELED),
  CANCELLING(6, OrderStatus.PENDING_CANCEL);

  private int orderStatus;
  private OrderStatus xchangeStatus;

  private BiboxOrderStatus(int orderStatus, OrderStatus xchangeStatus) {
    this.orderStatus = orderStatus;
    this.xchangeStatus = xchangeStatus;
  }

  public int asInt() {
    return orderStatus;
  }

  public OrderStatus getOrderStatus() {
    return xchangeStatus;
  }

  @JsonCreator
  public static BiboxOrderStatus fromInt(int orderStatus) {
    switch (orderStatus) {
      case 1:
        return PENDING;
      case 2:
        return PART_COMPLETED;
      case 3:
        return COMPLETED;
      case 4:
        return PART_CANCELED;
      case 5:
        return CANCELLED;
      case 6:
        return CANCELLING;
      default:
        throw new RuntimeException("Unexpected Bibox order status!");
    }
  }
}
