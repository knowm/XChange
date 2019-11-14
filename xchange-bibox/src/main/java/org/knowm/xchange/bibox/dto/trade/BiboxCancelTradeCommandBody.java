package org.knowm.xchange.bibox.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author odrotleff */
class BiboxCancelTradeCommandBody {

  @JsonProperty("orders_id")
  private String orderId;

  public BiboxCancelTradeCommandBody(String orderId) {
    super();
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
