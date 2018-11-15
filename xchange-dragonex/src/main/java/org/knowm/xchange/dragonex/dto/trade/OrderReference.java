package org.knowm.xchange.dragonex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderReference {

  @JsonProperty("symbol_id")
  public final long symbolId;
  /** order id you want to cancel */
  @JsonProperty("order_id")
  public final long orderId;

  public OrderReference(long symbolId, long orderId) {
    this.symbolId = symbolId;
    this.orderId = orderId;
  }

  @Override
  public String toString() {
    return "OrderReference [symbolId=" + symbolId + ", orderId=" + orderId + "]";
  }
}
