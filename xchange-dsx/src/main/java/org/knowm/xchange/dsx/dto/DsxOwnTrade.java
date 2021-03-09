package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class DsxOwnTrade {

  public final String symbol;
  private final Long id;
  private final String clientOrderId;
  private final Long orderId;
  private final DsxSide side;
  private final BigDecimal quantity;
  private final BigDecimal fee;
  private final BigDecimal price;
  private final Date timestamp;

  public DsxOwnTrade(
      @JsonProperty("id") Long id,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("orderId") Long orderId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") DsxSide side,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("timestamp") Date timestamp) {
    this.id = id;
    this.clientOrderId = clientOrderId;
    this.orderId = orderId;
    this.symbol = symbol;
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

  public DsxSide getSide() {
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
    return "DsxOwnTrade{"
        + "id="
        + id
        + ", clientOrderId='"
        + clientOrderId
        + '\''
        + ", orderId="
        + orderId
        + ", side="
        + side
        + ", quantity="
        + quantity
        + ", fee="
        + fee
        + ", price="
        + price
        + ", timestamp="
        + timestamp
        + '}';
  }
}
