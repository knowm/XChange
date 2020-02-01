package org.knowm.xchange.lgo.dto.order;

import java.time.Instant;
import java.util.StringJoiner;

public class LgoPlaceCancelOrder extends LgoPlaceOrder {

  private final String orderId;

  public LgoPlaceCancelOrder(long reference, String orderId, Instant timestamp) {
    super(reference, timestamp);
    this.orderId = orderId;
  }

  public String toPayload() {
    return new StringJoiner(",")
        .add("C")
        .add(orderId)
        .add(String.valueOf(getTimestamp().toEpochMilli()))
        .toString();
  }

  public String getOrderId() {
    return orderId;
  }
}
