package org.knowm.xchange.bibox.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;

/** @author odrotleff */
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
