package org.knowm.xchange.bibox.dto.trade;

import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author odrotleff
 */
public enum BiboxOrderSide {
  BID(1),
  ASK(2);

  private int orderSide;

  private BiboxOrderSide(int orderSide) {
    this.orderSide = orderSide;
  }

  public int asInt() {
    return orderSide;
  }

  public static BiboxOrderSide fromOrderType(OrderType orderType) {
    switch (orderType) {
      case BID:
        return BiboxOrderSide.BID;
      case ASK:
        return BiboxOrderSide.ASK;
      default:
        throw new ExchangeException("Order type " + orderType + " unsupported.");
    }
  }
}
