package org.knowm.xchange.coinjar.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinjarFill {
  public final String estimatedFee;
  public final String liquidity;
  public final Long oid;
  public final String price;
  public final String productId;
  public final String side;
  public final String size;
  public final Long tid;
  public final String timestamp;
  public final String value;

  public CoinjarFill(
      @JsonProperty("estimated_fee") String estimatedFee,
      @JsonProperty("liquidity") String liquidity,
      @JsonProperty("oid") Long oid,
      @JsonProperty("price") String price,
      @JsonProperty("product_id") String productId,
      @JsonProperty("side") String side,
      @JsonProperty("size") String size,
      @JsonProperty("tid") Long tid,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("value") String value) {
    this.estimatedFee = estimatedFee;
    this.liquidity = liquidity;
    this.oid = oid;
    this.price = price;
    this.productId = productId;
    this.side = side;
    this.size = size;
    this.tid = tid;
    this.timestamp = timestamp;
    this.value = value;
  }
}
