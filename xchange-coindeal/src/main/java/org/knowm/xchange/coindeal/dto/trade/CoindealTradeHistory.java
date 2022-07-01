package org.knowm.xchange.coindeal.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoindealTradeHistory {

  @JsonProperty("id")
  private final String id;

  @JsonProperty("clientOrderId")
  private final String clientOrderId;

  @JsonProperty("orderId")
  private final String orderId;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("quantity")
  private final BigDecimal quantity;

  @JsonProperty("fee")
  private final BigDecimal fee;

  @JsonProperty("price")
  private final BigDecimal price;

  @JsonProperty("timestamp")
  private final String timestamp;

  public CoindealTradeHistory(
      @JsonProperty("id") String id,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("orderId") String orderId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("timestamp") String timestamp) {
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

  public String getId() {
    return id;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getSide() {
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

  public String getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "CoindealTradeHistory{"
        + "id='"
        + id
        + '\''
        + ", clientOrderId='"
        + clientOrderId
        + '\''
        + ", orderId='"
        + orderId
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", side='"
        + side
        + '\''
        + ", quantity='"
        + quantity
        + '\''
        + ", fee='"
        + fee
        + '\''
        + ", price='"
        + price
        + '\''
        + ", timestamp='"
        + timestamp
        + '\''
        + '}';
  }
}
