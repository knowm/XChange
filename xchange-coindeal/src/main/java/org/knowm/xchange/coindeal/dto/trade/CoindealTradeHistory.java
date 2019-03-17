package org.knowm.xchange.coindeal.dto.trade;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.Map;

public class CoindealTradeHistory {

  @JsonProperty("id")
  private final Long id;

  @JsonProperty("clientOrderId")
  private final String clientOrderId;

  @JsonProperty("orderId")
  private final String orderId;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("quantity")
  private final String quantity;

  @JsonProperty("fee")
  private final String fee;

  @JsonProperty("price")
  private final String price;

  @JsonProperty("timestamp")
  private final String timestamp;

  public CoindealTradeHistory(
      @JsonProperty("id") Long id,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("orderId") String orderId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("quantity") String quantity,
      @JsonProperty("fee") String fee,
      @JsonProperty("price") String price,
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

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  @JsonProperty("clientOrderId")
  public String getClientOrderId() {
    return clientOrderId;
  }

  @JsonProperty("orderId")
  public String getOrderId() {
    return orderId;
  }

  @JsonProperty("symbol")
  public String getSymbol() {
    return symbol;
  }

  @JsonProperty("price")
  public String getPrice() {
    return price;
  }

  @JsonProperty("timestamp")
  public String getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "CoindealTradeHistory{"
        + "id="
        + id
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
