package org.knowm.xchange.coindeal.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoindealOrder {

  @JsonProperty("id")
  private final String id;

  @JsonProperty("clientOrderId")
  private final String clientOrderId;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("status")
  private final String status;

  @JsonProperty("quantity")
  private final double quantity;

  @JsonProperty("price")
  private final double price;

  public CoindealOrder(
          @JsonProperty("id") String id,
          @JsonProperty("clientOrderId") String clientOrderId,
          @JsonProperty("symbol") String symbol,
          @JsonProperty("side") String side,
          @JsonProperty("status") String status,
          @JsonProperty("quantity") double quantity,
          @JsonProperty("price") double price) {
    this.id = id;
    this.clientOrderId = clientOrderId;
    this.symbol = symbol;
    this.side = side;
    this.status = status;
    this.quantity = quantity;
    this.price = price;
  }

    public String getId() {
        return id;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSide() {
        return side;
    }

    public String getStatus() {
        return status;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "CoindealOrder{" +
                "id='" + id + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", side='" + side + '\'' +
                ", status='" + status + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
