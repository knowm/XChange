package org.knowm.xchange.coingi.dto.request;

import java.math.BigDecimal;

public class PlaceLimitOrderRequest extends AuthenticatedRequest {
  private String currencyPair;
  private int type;
  private BigDecimal price;
  private BigDecimal volume;

  public String getCurrencyPair() {
    return currencyPair;
  }

  public PlaceLimitOrderRequest setCurrencyPair(String pair) {
    this.currencyPair = pair;
    return this;
  }

  public int getType() {
    return type;
  }

  public PlaceLimitOrderRequest setOrderType(int type) {
    this.type = type;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public PlaceLimitOrderRequest setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public PlaceLimitOrderRequest setVolume(BigDecimal volume) {
    this.volume = volume;
    return this;
  }
}
