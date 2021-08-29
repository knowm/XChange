package org.knowm.xchange.ascendex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AscendexCancelOrderRequestPayload {

  @JsonProperty("orderId")
  private final String orderId;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("time")
  private final Long time;

  public AscendexCancelOrderRequestPayload(
      @JsonProperty("orderId") String orderId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("time") Long time) {
    this.orderId = orderId;
    this.symbol = symbol;
    this.time = time;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getSymbol() {
    return symbol;
  }

  public Long getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "AscendexCancelOrderRequestPayload{"
        + ", orderId='"
        + orderId
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", time="
        + time
        + '}';
  }
}
