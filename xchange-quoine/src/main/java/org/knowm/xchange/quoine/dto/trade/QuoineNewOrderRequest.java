package org.knowm.xchange.quoine.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoineNewOrderRequest {

  @JsonProperty("order_type")
  private final String orderType;//  Values: limit, market or market_with_range

  @JsonProperty("product_id")
  private final int productId;

  @JsonProperty("side")
  private final String side; // buy or sell

  @JsonProperty("quantity")
  private final BigDecimal quantity;//  Amount of BTC you want to trade.

  @JsonProperty("price")
  private final BigDecimal price; //  Price of BTC you want to trade.

  //  @JsonProperty("price_range")
  //  private final boolean priceRange;

  public QuoineNewOrderRequest(String orderType, int productId, String side, BigDecimal quantity, BigDecimal price) {
    this.orderType = orderType;
    this.productId = productId;
    this.side = side;
    this.quantity = quantity;
    this.price = price;
  }

  public String getOrderType() {
    return orderType;
  }

  public int getProductId() {
    return productId;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "QuoineNewOrderRequest [orderType=" + orderType + ", productId=" + productId + ", side=" + side + ", quantity=" + quantity + ", price="
        + price + "]";
  }

}
