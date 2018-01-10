package org.knowm.xchange.abucoins.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: bryant_harris
 */
public class AbucoinsOrder {
  String id;
  BigDecimal price;
  BigDecimal size;
  String productID;
  Side side;
  Type type;
  TimeInForce timeInForce;
  boolean postOnly;
  String createdAt;
  BigDecimal filledSize;
  Status status;
  boolean settled;
        
  public AbucoinsOrder(@JsonProperty("id") String id,
                       @JsonProperty("price") BigDecimal price,
                       @JsonProperty("size") BigDecimal size,
                       @JsonProperty("product_id") String productID,
                       @JsonProperty("side") Side side,
                       @JsonProperty("type") Type type,
                       @JsonProperty("time_in_force") TimeInForce timeInForce,
                       @JsonProperty("post_only") boolean postOnly,
                       @JsonProperty("created_at") String createdAt,
                       @JsonProperty("filled_size") BigDecimal filledSize,
                       @JsonProperty("status") Status status,
                       @JsonProperty("settled") boolean settled) {
    super();
    this.id = id;
    this.price = price;
    this.size = size;
    this.productID = productID;
    this.side = side;
    this.type = type;
    this.timeInForce = timeInForce;
    this.postOnly = postOnly;
    this.createdAt = createdAt;
    this.filledSize = filledSize;
    this.status = status;
    this.settled = settled;
  }
  
  public String getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getProductID() {
    return productID;
  }

  public Side getSide() {
    return side;
  }

  public Type getType() {
    return type;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public boolean isPostOnly() {
    return postOnly;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getFilledSize() {
    return filledSize;
  }

  public Status getStatus() {
    return status;
  }

  public boolean isSettled() {
    return settled;
  }

  @Override
  public String toString() {
    return "AbucoinsOrder [id=" + id + ", price=" + price + ", size=" + size + ", productID=" + productID + ", side="
        + side + ", type=" + type + ", timeInForce=" + timeInForce + ", postOnly=" + postOnly + ", createdAt="
        + createdAt + ", filledSize=" + filledSize + ", status=" + status + ", settled=" + settled + "]";
  }

  public enum Side {
    buy, sell
  }
  
  public enum Type {
    limit, market;
  }

  public enum TimeInForce {
    GTC, GTT, IOC, FOK
  }
  
  public enum Status {
    pending, open, done, rejected;
  }
}
