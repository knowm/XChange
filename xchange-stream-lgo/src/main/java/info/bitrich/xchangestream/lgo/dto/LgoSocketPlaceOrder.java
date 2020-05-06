package info.bitrich.xchangestream.lgo.dto;

import org.knowm.xchange.lgo.dto.order.LgoEncryptedOrder;

public class LgoSocketPlaceOrder {

  private final LgoEncryptedOrder order;

  public LgoSocketPlaceOrder(LgoEncryptedOrder encryptedOrder) {
    this.order = encryptedOrder;
  }

  public String getType() {
    return "placeorder";
  }

  public LgoEncryptedOrder getOrder() {
    return order;
  }
}
