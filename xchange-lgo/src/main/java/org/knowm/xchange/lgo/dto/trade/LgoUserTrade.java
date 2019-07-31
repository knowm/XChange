package org.knowm.xchange.lgo.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LgoUserTrade {

  private final String id;
  private final String orderId;
  private final String productId;
  private final String price;
  private final String quantity;
  private final String creationDate;
  private final String fees;
  private final String side;
  private final String liquidity;

  public LgoUserTrade(
      @JsonProperty("id") String id,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("product_id") String productId,
      @JsonProperty("price") String price,
      @JsonProperty("quantity") String quantity,
      @JsonProperty("creation_date") String creationDate,
      @JsonProperty("fees") String fees,
      @JsonProperty("side") String side,
      @JsonProperty("liquidity") String liquidity) {
    this.id = id;
    this.orderId = orderId;
    this.productId = productId;
    this.price = price;
    this.quantity = quantity;
    this.creationDate = creationDate;
    this.fees = fees;
    this.side = side;
    this.liquidity = liquidity;
  }

  public String getId() {
    return id;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getProductId() {
    return productId;
  }

  public String getPrice() {
    return price;
  }

  public String getQuantity() {
    return quantity;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public String getFees() {
    return fees;
  }

  public String getSide() {
    return side;
  }

  public String getLiquidity() {
    return liquidity;
  }
}
