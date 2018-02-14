package org.knowm.xchange.bibox.dto.trade;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author odrotleff
 */
class BiboxCancelTradeCommandBody {

  @JsonProperty("orders_id")
  private BigInteger orderId;

  public BiboxCancelTradeCommandBody(BigInteger orderId) {
    super();
    this.orderId = orderId;
  }

  public BigInteger getOrderId() {
    return orderId;
  }
}