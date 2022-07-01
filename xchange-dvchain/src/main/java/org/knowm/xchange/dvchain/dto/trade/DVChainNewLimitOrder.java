package org.knowm.xchange.dvchain.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DVChainNewLimitOrder {
  private String side;
  private BigDecimal qty;
  private String asset;
  private BigDecimal limitPrice;
  private String orderType;

  public DVChainNewLimitOrder(
      @JsonProperty("side") String side,
      @JsonProperty("limitPrice") BigDecimal limitPrice,
      @JsonProperty("qty") BigDecimal qty,
      @JsonProperty("asset") String asset) {
    this.asset = asset;
    this.limitPrice = limitPrice;
    this.qty = qty;
    this.side = side;
    this.orderType = "limit";
  }

  public BigDecimal getQty() {
    return qty;
  }

  public String getAsset() {
    return asset;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getLimitPrice() {
    return limitPrice;
  }

  public String getOrderType() {
    return orderType;
  }
}
