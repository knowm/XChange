package org.knowm.xchange.hitbtc.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.knowm.xchange.hitbtc.dto.general.HitbtcSide;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcOrder {

  private final Long id;
  private final String clientOrderId;
  private final String symbol;
  private final HitbtcSide side;
  private final String status;
  private final String type;
  private final String timeInForce;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal cumQuantity;
  private final Date createdAt;
  private final Date updatedAt;

  public HitbtcOrder(@JsonProperty("id") Long id, @JsonProperty("clientOrderId") String clientOrderId, @JsonProperty("symbol") String symbol,
      @JsonProperty("side") HitbtcSide side, @JsonProperty("status") String status, @JsonProperty("type") String type,
      @JsonProperty("timeInForce") String timeInForce, @JsonProperty("price") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("cumQuantity") BigDecimal cumQuantity, @JsonProperty("createdAt") Date createdAt, @JsonProperty("updatedAt") Date updatedAt) {

    this.id = id;
    this.clientOrderId = clientOrderId;
    this.symbol = symbol;
    this.side = side;
    this.status = status;
    this.type = type;
    this.timeInForce = timeInForce;
    this.price = price;
    this.quantity = quantity;
    this.cumQuantity = cumQuantity;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public String getSymbol() {
    return symbol;
  }

  public HitbtcSide getSide() {
    return side;
  }

  public String getStatus() {
    return status;
  }

  public String getType() {
    return type;
  }

  public String getTimeInForce() {
    return timeInForce;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getCumQuantity() {
    return cumQuantity;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this)
        .append("id", id)
        .append("clientOrderId", clientOrderId)
        .append("symbol", symbol)
        .append("side", side)
        .append("status", status)
        .append("type", type)
        .append("timeInForce", timeInForce)
        .append("price", price)
        .append("quantity", quantity)
        .append("cumQuantity", cumQuantity)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }
}
