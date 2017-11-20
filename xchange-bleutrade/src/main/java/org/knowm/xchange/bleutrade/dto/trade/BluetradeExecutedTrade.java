package org.knowm.xchange.bleutrade.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BluetradeExecutedTrade {
  public final String orderId;
  public final String exchange;
  public final String type;
  public final BigDecimal quantity;
  public final String quantityRemaining;
  public final BigDecimal price;
  public final String status;
  public final String created;
  public final BigDecimal quantityBaseTraded;
  public final String comments;

  public BluetradeExecutedTrade(@JsonProperty("OrderId") String orderId, @JsonProperty("Exchange") String exchange, @JsonProperty("Type") String type,
      @JsonProperty("Quantity") BigDecimal quantity, @JsonProperty("QuantityRemaining") String quantityRemaining,
      @JsonProperty("Price") BigDecimal price, @JsonProperty("Status") String status, @JsonProperty("Created") String created,
      @JsonProperty("QuantityBaseTraded") BigDecimal quantityBaseTraded, @JsonProperty("Comments") String comments) {
    this.orderId = orderId;
    this.exchange = exchange;
    this.type = type;
    this.quantity = quantity;
    this.quantityRemaining = quantityRemaining;
    this.price = price;
    this.status = status;
    this.created = created;
    this.quantityBaseTraded = quantityBaseTraded;
    this.comments = comments;
  }
}
