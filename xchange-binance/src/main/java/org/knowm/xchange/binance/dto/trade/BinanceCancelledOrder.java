package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BinanceCancelledOrder {

  public final String symbol;
  public final String origClientOrderId;
  public final long orderId;
  public final String clientOrderId;
  public String price;
  public String origQty;
  public String executedQty;
  public String cummulativeQuoteQty;
  public String status;
  public String timeInForce;
  public String type;
  public String side;

  public BinanceCancelledOrder(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("origClientOrderId") String origClientOrderId,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("price") String price,
      @JsonProperty("origQty") String origQty,
      @JsonProperty("executedQty") String executedQty,
      @JsonProperty("cummulativeQuoteQty") String cummulativeQuoteQty,
      @JsonProperty("status") String status,
      @JsonProperty("timeInForce") String timeInForce,
      @JsonProperty("type") String type,
      @JsonProperty("side") String side) {
    super();
    this.symbol = symbol;
    this.origClientOrderId = origClientOrderId;
    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
    this.price = price;
    this.origQty = origQty;
    this.executedQty = executedQty;
    this.cummulativeQuoteQty = cummulativeQuoteQty;
    this.status = status;
    this.timeInForce = timeInForce;
    this.type = type;
    this.side = side;
  }
}
