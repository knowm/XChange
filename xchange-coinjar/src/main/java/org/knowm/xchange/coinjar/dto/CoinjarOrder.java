package org.knowm.xchange.coinjar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinjarOrder {
  public final Long oid;
  public final String orderType;
  public final String productId;
  public final String orderSide;
  public final String price;
  public final String size;
  public final String timeInForce;
  public final String filled;
  public final String status;
  public final String ref;
  public final String timestamp;

  public CoinjarOrder(
      @JsonProperty("oid") Long oid,
      @JsonProperty("type") String orderType,
      @JsonProperty("product_id") String productId,
      @JsonProperty("side") String orderSide,
      @JsonProperty("price") String price,
      @JsonProperty("size") String size,
      @JsonProperty("time_in_force") String timeInForce,
      @JsonProperty("filled") String filled,
      @JsonProperty("status") String status,
      @JsonProperty("ref") String ref,
      @JsonProperty("timestamp") String timestamp) {
    this.oid = oid;
    this.orderType = orderType;
    this.productId = productId;
    this.orderSide = orderSide;
    this.price = price;
    this.size = size;
    this.timeInForce = timeInForce;
    this.filled = filled;
    this.status = status;
    this.ref = ref;
    this.timestamp = timestamp;
  }
}
