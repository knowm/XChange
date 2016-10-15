package org.knowm.xchange.gdax.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GDAXPlaceOrder {
  @JsonProperty("size")
  private final BigDecimal size;
  @JsonProperty("price")
  private final BigDecimal price;
  @JsonProperty("side")
  private final String side;
  @JsonProperty("product_id")
  private final String productId;
  @JsonProperty("type")
  private final String type;

  public GDAXPlaceOrder(BigDecimal size, BigDecimal price, String side, String productId, String type) {
    this.size = size;
    this.price = price;
    this.side = side;
    this.productId = productId;
    this.type = type;
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

  public String getType() {
    return type;
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
    builder.append(", type=");
    builder.append(type);
    builder.append(", productId=");
    builder.append(productId);
    builder.append("]");
    return builder.toString();
  }

}
