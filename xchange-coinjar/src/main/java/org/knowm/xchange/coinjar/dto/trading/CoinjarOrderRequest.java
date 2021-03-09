package org.knowm.xchange.coinjar.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinjarOrderRequest {
  @JsonProperty("product_id")
  public final String productId;

  @JsonProperty("type")
  public final String orderType;

  @JsonProperty("side")
  public final String side;

  @JsonProperty("price")
  public final String price;

  @JsonProperty("size")
  public final String size;

  @JsonProperty("time_in_force")
  public final String timeInForce;

  @JsonProperty("ref")
  public final String ref;

  public CoinjarOrderRequest(
      String productId,
      String orderType,
      String side,
      String price,
      String size,
      String timeInForce,
      String ref) {
    this.productId = productId;
    this.orderType = orderType;
    this.side = side;
    this.price = price;
    this.size = size;
    this.timeInForce = timeInForce;
    this.ref = ref;
  }
}
