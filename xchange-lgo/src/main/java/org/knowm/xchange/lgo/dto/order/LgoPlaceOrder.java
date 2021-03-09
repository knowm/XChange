package org.knowm.xchange.lgo.dto.order;

import java.time.Instant;

public abstract class LgoPlaceOrder {

  private final long reference;
  private final Instant timestamp;

  public LgoPlaceOrder(long reference, Instant timestamp) {
    this.reference = reference;
    this.timestamp = timestamp;
  }

  public abstract String toPayload();

  public long getReference() {
    return reference;
  }

  public Instant getTimestamp() {
    return timestamp;
  }
}
