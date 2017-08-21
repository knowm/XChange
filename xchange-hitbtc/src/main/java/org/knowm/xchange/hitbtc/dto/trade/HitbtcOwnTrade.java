package org.knowm.xchange.hitbtc.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.knowm.xchange.hitbtc.dto.general.HitbtcSide;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcOwnTrade {

  private final Long id;
  private final String clientOrderId;
  private final Long orderId;
  private final HitbtcSide side;
  private final BigDecimal quantity;
  private final BigDecimal fee;
  private final BigDecimal price;
  private final Date timestamp;

  public HitbtcOwnTrade(@JsonProperty("id") Long id, @JsonProperty("clientOrderId") String clientOrderId, @JsonProperty("orderId") Long orderId,
      @JsonProperty("side") HitbtcSide side, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("price") BigDecimal price, @JsonProperty("timestamp") Date timestamp) {
    this.id = id;
    this.clientOrderId = clientOrderId;
    this.orderId = orderId;
    this.side = side;
    this.quantity = quantity;
    this.fee = fee;
    this.price = price;
    this.timestamp = timestamp;
  }

  public Long getId() {
    return id;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public HitbtcSide getSide() {
    return side;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this)
        .append("id", id)
        .append("clientOrderId", clientOrderId)
        .append("orderId", orderId)
        .append("side", side)
        .append("quantity", quantity)
        .append("fee", fee)
        .append("id", id)
        .append("price", price)
        .append("timestamp", timestamp)
        .toString();
  }
}
