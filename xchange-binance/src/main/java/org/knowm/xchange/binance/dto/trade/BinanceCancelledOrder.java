package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BinanceCancelledOrder {

  public final String symbol;
  public final String origClientOrderId;
  public final long orderId;
  public final String clientOrderId;

  public BinanceCancelledOrder(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("origClientOrderId") String origClientOrderId,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("clientOrderId") String clientOrderId) {
    super();
    this.symbol = symbol;
    this.origClientOrderId = origClientOrderId;
    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
  }
}
