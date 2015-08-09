package com.xeiam.xchange.coinbaseex.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbaseExPlaceOrder {
  @JsonProperty("size")
  private final BigDecimal size;
  @JsonProperty("price")
  private final BigDecimal price;
  @JsonProperty("side")
  private final String side;
  @JsonProperty("product_id")
  private final String productId;

  public CoinbaseExPlaceOrder(BigDecimal size, BigDecimal price, String side, String productId) {
    this.size = size;
    this.price = price;
    this.side = side;
    this.productId = productId;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getSide() {
    return side;
  }

  public String getProductId() {
    return productId;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoinbaseExPlaceOrder [size=");
    builder.append(size);
    builder.append(", price=");
    builder.append(price);
    builder.append(", side=");
    builder.append(side);
    builder.append(", productId=");
    builder.append(productId);
    builder.append("]");
    return builder.toString();
  }

}
