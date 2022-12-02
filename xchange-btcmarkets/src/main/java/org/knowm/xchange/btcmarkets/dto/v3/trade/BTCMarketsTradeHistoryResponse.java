package org.knowm.xchange.btcmarkets.dto.v3.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BTCMarketsTradeHistoryResponse {

  public final String id;
  public final String marketId;
  public final String timestamp;
  public final BigDecimal price;
  public final BigDecimal amount;
  public final String side;
  public final BigDecimal fee;
  public final String orderId;
  public final String liquidityType;
  public final String clientOrderId;

  public BTCMarketsTradeHistoryResponse(
      @JsonProperty("id") String id,
      @JsonProperty("marketId") String marketId,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("side") String side,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("orderId") String orderId,
      @JsonProperty("liquidityType") String liquidityType,
      @JsonProperty("clientOrderId") String clientOrderId) {
    this.id = id;
    this.marketId = marketId;
    this.timestamp = timestamp;
    this.price = price;
    this.amount = amount;
    this.side = side;
    this.fee = fee;
    this.orderId = orderId;
    this.liquidityType = liquidityType;
    this.clientOrderId = clientOrderId;
  }
}
