package info.bitrich.xchangestream.coinmate.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmateWebsocketOpenOrder {

  @JsonProperty("amount")
  private final double amount;

  @JsonProperty("date")
  private final long timestamp;

  @JsonProperty("hidden")
  private final boolean isHidden;

  @JsonProperty("id")
  private final String id;

  @JsonProperty("original")
  private final double originalOrderSize;

  @JsonProperty("price")
  private final double price;

  @JsonProperty("type")
  private final String orderType;

  @JsonProperty("stopPrice")
  @JsonIgnore
  private final double stopPrice;

  @JsonProperty("trailing")
  private final boolean isTrailing;

  @JsonProperty("originalStopPrice")
  @JsonIgnore
  private final double originalStopPrice;

  @JsonProperty("priceAtStopLossCreation")
  @JsonIgnore
  private final double priceAtStopLossCreation;

  @JsonProperty("priceAtStopLossUpdate")
  @JsonIgnore
  private final double priceAtStopLossUpdate;

  @JsonProperty("trailingUpdatedTimestamp")
  @JsonIgnore
  private final long trailingUpdatedTimestamp;

  @JsonProperty("orderChangePushEvent")
  private final String orderChangePushEvent;

  @JsonCreator
  public CoinmateWebsocketOpenOrder(
      @JsonProperty("amount") double amount,
      @JsonProperty("date") long timestamp,
      @JsonProperty("hidden") boolean isHidden,
      @JsonProperty("id") String id,
      @JsonProperty("original") double originalOrderSize,
      @JsonProperty("price") double price,
      @JsonProperty("type") String orderType,
      @JsonProperty("stopPrice") double stopPrice,
      @JsonProperty("trailing") boolean isTrailing,
      @JsonProperty("originalStopPrice") double originalStopPrice,
      @JsonProperty("priceAtStopLossCreation") double priceAtStopLossCreation,
      @JsonProperty("priceAtStopLossUpdate") double priceAtStopLossUpdate,
      @JsonProperty("trailingUpdatedTimestamp") long trailingUpdatedTimestamp,
      @JsonProperty("orderChangePushEvent") String orderChangePushEvent) {
    this.amount = amount;
    this.timestamp = timestamp;
    this.isHidden = isHidden;
    this.id = id;
    this.originalOrderSize = originalOrderSize;
    this.price = price;
    this.orderType = orderType;
    this.stopPrice = stopPrice;
    this.isTrailing = isTrailing;
    this.originalStopPrice = originalStopPrice;
    this.priceAtStopLossCreation = priceAtStopLossCreation;
    this.priceAtStopLossUpdate = priceAtStopLossUpdate;
    this.trailingUpdatedTimestamp = trailingUpdatedTimestamp;
    this.orderChangePushEvent = orderChangePushEvent;
  }

  public double getAmount() {
    return this.amount;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public boolean isHidden() {
    return this.isHidden;
  }

  public String getId() {
    return this.id;
  }

  public double getOriginalOrderSize() {
    return this.originalOrderSize;
  }

  public double getPrice() {
    return this.price;
  }

  public String getOrderType() {
    return this.orderType;
  }

  public double getStopPrice() {
    return this.stopPrice;
  }

  public boolean isTrailing() {
    return this.isTrailing;
  }

  public double getOriginalStopPrice() {
    return this.originalStopPrice;
  }

  public double getPriceAtStopLossCreation() {
    return this.priceAtStopLossCreation;
  }

  public double getPriceAtStopLossUpdate() {
    return this.priceAtStopLossUpdate;
  }

  public long getTrailingUpdatedTimestamp() {
    return this.trailingUpdatedTimestamp;
  }

  public String getOrderChangePushEvent() {
    return this.orderChangePushEvent;
  }

  @Override
  public String toString() {
    return "CoinmateWebsocketOpenOrder{"
        + "amount="
        + amount
        + ", timestamp="
        + timestamp
        + ", isHidden="
        + isHidden
        + ", id='"
        + id
        + '\''
        + ", originalOrderSize="
        + originalOrderSize
        + ", price="
        + price
        + ", orderType="
        + orderType
        + ", stopPrice="
        + stopPrice
        + ", isTrailing="
        + isTrailing
        + ", originalStopPrice="
        + originalStopPrice
        + ", priceAtStopLossCreation="
        + priceAtStopLossCreation
        + ", priceAtStopLossUpdate="
        + priceAtStopLossUpdate
        + ", trailingUpdatedTimestamp="
        + trailingUpdatedTimestamp
        + '}';
  }
}
