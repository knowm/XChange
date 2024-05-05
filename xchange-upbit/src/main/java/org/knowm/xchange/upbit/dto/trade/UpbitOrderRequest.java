package org.knowm.xchange.upbit.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpbitOrderRequest {

  @JsonProperty("market")
  protected String marketId;

  @JsonProperty("side")
  protected String side;

  @JsonProperty("volume")
  protected String volume;

  @JsonProperty("price")
  protected String price;

  @JsonProperty("ord_type")
  protected String orderType;

  public UpbitOrderRequest() {}

  public UpbitOrderRequest(
      String marketId, String side, String volume, String price, String orderType) {
    this.marketId = marketId;
    this.side = side;
    this.volume = volume;
    this.price = price;
    this.orderType = orderType;
  }

  public String getMarketId() {
    return marketId;
  }

  public void setMarketId(String marketId) {
    this.marketId = marketId;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getVolume() {
    return volume;
  }

  public void setVolume(String volume) {
    this.volume = volume;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }
}
